package com.techelevator;

public class Gum extends Items{
    public Gum(String slotLocation, String productName, double price, int quantity) {
        super(slotLocation, productName, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Chew Chew, Yum!";
    }
}
