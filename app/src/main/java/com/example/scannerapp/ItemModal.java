package com.example.scannerapp;

public class ItemModal {

    // variables for our itemname,
    // price,code and id.
    public String itemName;
    public String itemPrice;
    public String itemCode;
    private int id;
    public ItemModal(){}

    // creating getter and setter methods
    public String getitemName() {
        return itemName;
    }

    public void setitemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public ItemModal(String itemName, String itemPrice, String itemCode) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCode = itemCode;
    }
}