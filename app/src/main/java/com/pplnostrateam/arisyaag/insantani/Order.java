package com.pplnostrateam.arisyaag.insantani;

import java.util.Date;

/**
 * Created by Adrian on 5/25/2016.
 */
public class Order {

    private long id;
    private String orderNumber;
    private String farmerName;
    private String created;
    private String vegetableName;
    private Vegetable vegetable;
    private String location;
    private double latitude;
    private double longitude;
    private int orderStatus;
    private String note;
    private int price;
    private int quantity;

    public Order(long id, String orderNumber, String created, Vegetable vegetable, String location, double latitude, double longitude, int orderStatus, String note, int price, int quantity) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.created = created;
        this.vegetable = vegetable;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orderStatus = orderStatus;
        this.note = note;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(String orderNumber, String created, String vegetableName, String location, String farmerName) {
        this.orderNumber = orderNumber;
        this.created = created;
        this.vegetableName = vegetableName;
        this.location = location;
        this.farmerName = farmerName;
    }

    public Order() {

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String  created) {
        this.created = created;
    }

    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
