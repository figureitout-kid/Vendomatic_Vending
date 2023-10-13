package com.techelevator;

import com.techelevator.Items;

public class Drink extends Items {

    public Drink(String slotLocation, String productName, double price, int quantity) {
        super(slotLocation, productName, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Glug Glug, Yum!";
    }
}
