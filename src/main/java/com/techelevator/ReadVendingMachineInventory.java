package com.techelevator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadVendingMachineInventory {
    public static void main(String[] args) {
        String filePath = "vendingmachine.csv";
        List<Items> vendingMachineItems = readItemsFromCSV(filePath);
    }

    public static List<Items> readItemsFromCSV(String filePath) {
        List<Items> items = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    String slotLocation = parts[0].trim();
                    String productName = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    String type = parts[3].trim();
                    int quantity = 5; // Initialize the quantity to 5

                    Items item = new Items(slotLocation, productName, price, Items.ItemType.valueOf(type.toUpperCase()), quantity);
                    items.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
