package com.techelevator;

import java.text.DecimalFormat;
public abstract class Items {
    private String slotLocation;
    private String productName;
    private double price;
    private int quantity;
    private int quantitySold;
    private int MAX_QUANTITY = 5;

    //setter
    public Items(String slotLocation, String productName, double price, int quantity) {
        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    //getters
    public String getSlotLocation() {
        return slotLocation;
    }
    public String getProductName() {
        return productName;
    }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getQuantitySold() { return quantitySold; }


    //methods
    public void dispense() {
        if (quantity > 0) {
            quantity--;
            System.out.println(getMessage());
        }
    }

    public boolean isSoldOut() {
        return quantity == 0;
    }

    public abstract String getMessage();

    //toString method to properly print Vending Machine Items from Menu Option 1
    public String toString() {
        DecimalFormat currency = new DecimalFormat("#0.00");
        return "Slot: " + slotLocation +
                ", Name: " + productName +
                ", Price: $" + currency.format(price) +
                ", Quantity: " + quantity;
    }

    public void restock() {
        quantity = MAX_QUANTITY;
    }

    public void incrementQuantitySold() {
        this.quantitySold++;
    }
}

