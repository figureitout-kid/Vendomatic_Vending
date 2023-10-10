package com.techelevator;

import com.techelevator.view.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private List<Items> vendingMachineItems;
	private Map<String, Items> itemLocator = new HashMap<>();
	private double balance;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.vendingMachineItems = ReadVendingMachineInventory.readItemsFromCSV("vendingmachine.csv");
		this.itemLocator = new HashMap<>();
		this.balance = 0;

		for (Items item : vendingMachineItems) {
			itemLocator.put(item.getSlotLocation(), item);
		}
	}


	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			switch (choice) {
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayVendingMachineItems();
					break;
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseMenu();
					break;
				case MAIN_MENU_OPTION_EXIT:
					System.out.println("Thank you for using this vending machine. Have a nice day...");
					// do we need to add logic here to restock the entire vending machine?- we would need to iterate through all items?
					System.exit(0);
					break;
					// if user doesn't select 1-3
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
			}
		}
	}


	private void displayVendingMachineItems() {
		for (Items item : vendingMachineItems) {
			System.out.println(item.toString());
		}
	}

	private void purchaseMenu() {
		while (true) {
			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			switch (purchaseChoice){
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					//allows user to feed money into machine in whole dollar amounts, returns 'current money provided'
					feedMoney();
					break;

				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					// grabs the product from slot location, dispenses, and then returns to PurchaseMenu
					// adds to log after selecting *************************************************************** to do
					selectProduct();
					break;
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
//					finishTransaction(); /* to do****************************************
					// returns change using as few coins possible
					// updates current balance to 0;
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
			}
		}
	}
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);

		cli.run();
	}



//methods left to implement and call in the purchase menu

	private void feedMoney() {
		boolean continueFeeding = true;

		while (continueFeeding) {
			System.out.println("Enter the dollar amount you want to feed (1, 2, 5, 10) or type 'done' to go back: ");
			String input = menu.getInput();

			if (input.equalsIgnoreCase("done")) {
				continueFeeding = false;
				break;
			}

			try {
				int insertedBill = Integer.parseInt(input);

				if (insertedBill == 1 || insertedBill == 2 || insertedBill == 5 || insertedBill == 10){
					balance += insertedBill;
					System.out.println("Current Money Provided: $" + balance);
				}
				else {
					System.out.println("Invalid currency. Your money is no good here.");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Please enter a valid dollar amount.");
			}
		}
	}


	private void selectProduct() {
		displayVendingMachineItems();
		System.out.print("Enter the slot location of the item you'd like to purchase: ");
		// save input for location-- possibly need to change var name?
		String slotLocation = menu.getInput().toUpperCase();

		//ensure input is a legitimate slot location
		if (!itemLocator.containsKey(slotLocation)) {
			System.out.println("Invalid slot location. Pleas try again.");
			return;
		}
		//***may have discrepancy w/ Items vs String?????????????????????????????????????????????
		//grab name of the item and save as snack variable, by hashmap itemlocator
		Items snackSelection = itemLocator.get(slotLocation);

		// check if sold out
		if (snackSelection.isSoldOut()) {
			System.out.println("Sorry, " + snackSelection + " is sold out. Please make another selection.");
			// go back to Purchase menu
			return;
		}

		//ensure user has enough money
		if (balance < snackSelection.getPrice()){
			System.out.println("Oh no! You don't have enough money- please add more or select a different item.");
			return;
		}

		//subtract amount of snack from balance
		balance -= snackSelection.getPrice();
		//print item name, cost, and remaining balance
		System.out.println("Item: " + snackSelection.getproductName() + " $" + snackSelection.getPrice() + " Money Remaining: $" + balance);
		//dispense snack and show message, return to Purchase menu
		snackSelection.dispense();
	}


}

// should we have an option to go back one more menu, in the example of not having enough money and wanting to add more, can they?
// we also need to see if someone can buy more than one of the same thing, and ensure the balance is correct after one purchase