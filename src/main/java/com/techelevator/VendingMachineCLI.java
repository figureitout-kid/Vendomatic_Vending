package com.techelevator;

import com.techelevator.view.Menu;

import java.util.List;


public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_SELECT_PRODUCT};

	private Menu menu;
	private List<Items> vendingMachineItems;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.vendingMachineItems = ReadVendingMachineInventory.readItemsFromCSV("vendingmachine.csv");
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				displayVendingMachineItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchaseMenu();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Thank you for using this vending machine. Have a nice day...");
				System.exit(0);
			}
		}
	}

	private void displayVendingMachineItems() {
		for (Items item : vendingMachineItems) {
			System.out.println(item.toString());
		}
	}

	private void purchaseMenu() {
		while(true) {
			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				//implement logic for feeding money
			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				//implement logic for selecting and purchasing a product
			} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				//implement logic for finishing the transaction
				return;
			}
		}
	}


	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
