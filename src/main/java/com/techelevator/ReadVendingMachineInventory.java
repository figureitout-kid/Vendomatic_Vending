package com.techelevator;

import com.techelevator.view.VendingMachineItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadVendingMachineInventory {
    public static void main(String[] args) {
        String filePath = "vendingmachine.csv";
        List<VendingMachineItem> vendingMachineItems = readItemsFromCSV(filePath);
    }

    public static List<VendingMachineItem> readItemsFromCSV(String filePath) {
        List<VendingMachineItem> items = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    String slotLocation = parts[0].trim();
                    String productName = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    String type = parts[3].trim();

                    VendingMachineItem item = new VendingMachineItem(slotLocation, productName, price, type);
                    items.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
