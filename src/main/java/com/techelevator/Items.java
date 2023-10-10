package com.techelevator;

public class Items {
    private String slotLocation;
    private String productName;
    private double price;
    private ItemType type;
    private int quantity;
    private int MAX_QUANTITY = 5;

    //setter, maybe change the parameters, or need to read from inventory?
    public Items(String slotLocation, String productName, double price, ItemType type, int quantity) {
        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }


    // trying enum here, but this could potentially
    // be an interface, method getMessage()
    public enum ItemType {
        CHIP("Crunch Crunch, Yum!"),
        CANDY("Munch Munch, Yum!"),
        DRINK("Glug Glug, Yum!"),
        GUM("Chew Chew, Yum!");

        private final String message;

        ItemType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }




    //getters
    public String getSlotLocation() {
        return slotLocation;
    }

    public String getproductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public ItemType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    //methods

    //may need to change dispense to include/tie in item instead of using type, depending on vending machine code?
    public void dispense() {
        if (quantity > 0) {
            quantity--;
            System.out.println(type.getMessage());
        }
    }

    public boolean isSoldOut() {
        return quantity == 0;
    }

    //toString method to properly print Vending Machine Items from Menu Option 1
    public String toString() {
        return "Slot: " + slotLocation +
                ", Name: " + productName +
                ", Price: $" + price +
                ", Type: " + type +
                ", Quantity: " + quantity;
    }

    public void restock() {
        quantity = MAX_QUANTITY;
    }
}



// somewhere we need to read the vending machine inventory, and
// give information on items location/name/price/itemtype
//do we need a hashmap of items linked to their location?