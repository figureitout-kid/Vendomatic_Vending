package com.techelevator;

import com.techelevator.Items;

public class Candy extends Items {

    public Candy(String slotLocation, String productName, double price, int quantity) {
        super(slotLocation, productName, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Munch Munch, Yum!";
    }
}
