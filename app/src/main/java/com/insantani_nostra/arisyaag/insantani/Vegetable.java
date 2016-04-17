package com.insantani_nostra.arisyaag.insantani;

/**
 * Created by Suci Ayu on 4/17/2016.
 */
public class Vegetable {
    private String name, stock, price;

    public Vegetable(String name, String stock, String price){
        this.setName(name);
        this.setStock(stock);
        this.setPrice(price);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}