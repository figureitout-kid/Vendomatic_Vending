package com.techelevator;

public class Items {
    private String slotLocation;
    private String name;
    private int price;
    private ItemType type;
    private int quantity;

    // trying enum here, but this could potentially
    // be an interface, method getMessage()
    public enum ItemType {
        CHIP ("Crunch Crunch, Yum!"),
        CANDY ("Munch Munch, Yum!"),
        DRINK ("Glug Glug, Yum!"),
        GUM ("Chew Chew, Yum!");

        private final String message;

        ItemType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    //setter, maybe change the parameters, or need to read from inventory?
    public void Item(String slotLocation, String name, int price, ItemType type){
        this.slotLocation = slotLocation;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = 5;
    }

    //getters
    public String getSlotLocation() {
        return slotLocation;
    }
    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
    public ItemType getType(){
        return type;
    }
    public int getQuantity(){
        return quantity;
    }

    //methods

    //may need to change dispense to include/tie in item instead of using type, depending on vending machine code?
    public void dispense() {
        if (quantity > 0) {
            quantity --;
            System.out.println(type.getMessage());
            //* should we add a log step here??????*//
        }
    }
    public String isSoldOut(){
        if (quantity == 0){
            System.out.println( "SORRY " + name + " IS SOLD OUT");
        }
    }

}

// somewhere we need to read the vending machine inventory, and
// give information on items location/name/price/itemtype
//do we need a hashmap of items linked to their location?