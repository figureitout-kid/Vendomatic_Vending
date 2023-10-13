package com.techelevator;

public class Chip extends Items {

    public Chip(String slotLocation, String productName, double price, int quantity) {
        super(slotLocation, productName, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Crunch Crunch, Yum!";
    }
}
