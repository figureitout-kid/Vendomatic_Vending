package com.techelevator;

import com.techelevator.view.Menu;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;


public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";
	private static final String MAIN_MENU_OPTION_TECHNICIAN_LOGIN = "Technician Login";
	private static final String[] MAIN_MENU_OPTIONS_USER = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_TECHNICIAN_LOGIN};
	private static final String[] MAIN_MENU_OPTIONS_TECHNICIAN = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private List<Items> vendingMachineItems;
	private Map<String, Items> itemLocator = new HashMap<>();
	private boolean enableTechnicianMode = false;
	private static final String TECHNICIAN_PASSWORD = "4";
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
            String choice;

            if (enableTechnicianMode) {
                //Technician menu
				choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS_TECHNICIAN);
            } else {
				//User menu
                choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS_USER);
            }


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
                case MAIN_MENU_OPTION_SALES_REPORT:
					if (enableTechnicianMode) {
						generateSalesReport();
					} else {
						System.out.println("Invalid choice. Please try again.");
					}
                    break;
				case MAIN_MENU_OPTION_TECHNICIAN_LOGIN:
					if(isTechnicianMode()) {
						enableTechnicianMode = true;
					} else {
						System.out.println("Invalid choice. Please try again.");
					}
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

	private boolean isTechnicianMode() {
		System.out.print("Enter Technician password: ");
		String password = menu.getInput().trim();
		return "4".equals(password);
	}

	private void generateSalesReport() {
		double totalSales = 0;

		for (Items item : vendingMachineItems) {
			totalSales += item.getQuantitySold() * item.getPrice();
		}

		//Create a unique filename
		String timestamp =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName = "SalesReport_" + timestamp + ".txt";

		try (PrintWriter writer = new PrintWriter(fileName)) {
			for (Items item : vendingMachineItems) {
				writer.println(item.getproductName() + "|" + item.getQuantitySold());
			}

			writer.println();

			//Print total sales
			writer.println("TOTAL SALES $" + String.format("%.2f", totalSales));

			System.out.println("Sales report has been generated: " + fileName);
		} catch (FileNotFoundException e) {
			System.out.println("Error generating the sales report: " + e.getMessage());
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
					selectProduct();
					break;
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
					// returns change using as few coins possible
					// updates current balance to 0;
					finishTransaction();
					return;
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



	private void feedMoney() {
		boolean continueFeeding = true;

		while (continueFeeding) {
			System.out.println("Enter the dollar amount you want to feed (1, 2, 5, 10) or type 'done' to purchase: ");
			String input = menu.getInput().trim();

			if (input.equalsIgnoreCase("done")) {
				continueFeeding = false;
				break;
			}

			try {
				double insertedBill = Double.parseDouble(input);

				if (insertedBill == 1 || insertedBill == 2 || insertedBill == 5 || insertedBill == 10){
					balance += insertedBill;
					//log the deposit
					TransactionLogger.logDeposit(insertedBill, balance);
					System.out.println("Current Money Provided: $" + String.format("%.2f", balance));
				} else {
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
		String slotLocation = menu.getInput().toUpperCase().trim();

		//ensure input is a legitimate slot location
		if (!itemLocator.containsKey(slotLocation)) {
			System.out.println("Invalid slot location. Pleas try again.");
			return;
		}

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

		//increment quantitySold for the selected product
		snackSelection.incrementQuantitySold();

		//subtract amount of snack from balance
		balance -= snackSelection.getPrice();

		//testing this to address the missing 0 in the hundreds place
		DecimalFormat currency = new DecimalFormat("#0.00");
		String properDecimalBalance = currency.format(balance);

		//print item name, cost, and remaining balance
		System.out.println("Item: " + snackSelection.getproductName() + " $" + currency.format(snackSelection.getPrice()) + " Money Remaining: $" + properDecimalBalance);

		//log purchase
		TransactionLogger.logPurchase(snackSelection, balance);

		//dispense snack and show message, return to Purchase menu
		snackSelection.dispense();
	}

	//finish transaction option-- return change in least amount of change using quarters, dimes, nickels, print change amount
	//return balance to zero, return to main menu

	//convert dollar amount to actual change amount, then reduce balance by using modulo to find remainder
	//braxton- if you want me to walk this out, it hurt my brain
	private void finishTransaction(){
		double originalBalance = balance;
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;

		//convert balance to exact change amount
		int changeDue = (int) (balance * 100);

		//start with quarters first
		quarters = changeDue / 25;
		changeDue %= 25;

		//dimes
		dimes = changeDue / 10;
		changeDue %= 10;

		//nickels
		nickels = changeDue / 5;

		System.out.println("Your change is:");


		if (quarters == 1) {System.out.println (quarters + " Quarter");}
		if (quarters > 1) {System.out.println (quarters + " Quarters");}

		if (dimes == 1) {System.out.println (dimes + " Dime");}
		if (dimes > 1) {System.out.println (dimes + " Dimes");}

		if (nickels == 1) {System.out.println (nickels + " Nickel");}
		if (nickels > 1) {System.out.println (nickels + " Nickels");}

		//log the change given
		TransactionLogger.logGiveChange(originalBalance, 0);

		balance = 0.0;

		System.out.println("Thank you for using this vending machine. Enjoy!");
	}
}