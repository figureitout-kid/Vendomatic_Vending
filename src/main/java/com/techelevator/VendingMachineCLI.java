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

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.vendingMachineItems = ReadVendingMachineInventory.readItemsFromCSV("vendingmachine.csv");
		this.itemLocator = new HashMap<>();

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
//					feedMoney(); to do****************************************
					break;
					//ensure that the display shows of 'Current Money Provided'
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
//					selectProduct(); to do****************************************
					// grabs the product from slot location, dispenses, and then returns to PurchaseMenu
					// adds to log
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
//			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
//
//			if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
//				//implement logic for feeding money
//			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
//				//implement logic for selecting and purchasing a product
//			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
//				//implement logic for finishing the transaction
//				return;
//			}




	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);

		cli.run();
	}

	//checking the hashmap ability to locate an item
	public String itemGrabber(String slotLocation){
		Items snackSelection = itemLocator.get(slotLocation);

		if(snackSelection == null) {
			return "Item not found for slot: " + slotLocation;
		}
		return snackSelection.getproductName();
	}



}
