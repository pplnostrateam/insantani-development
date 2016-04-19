package com.pplnostrateam.arisyaag.insantani;

/**
 * Created by arisyaag on 4/13/16.
 */
public class Vegetable {
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    private int id;

    private String name;

    private int price;

    private int stock;

    public Vegetable() { }

    public Vegetable(int id) {
        this.id = id;
    }

    public Vegetable(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int value) {
        this.stock = value;
    }
}
