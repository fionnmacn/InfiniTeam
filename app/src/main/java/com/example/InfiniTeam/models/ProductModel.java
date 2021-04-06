package com.example.InfiniTeam.models;

import java.io.Serializable;

public class ProductModel  implements Serializable {

    private String id;
    private String name;
    private String description;
    private String extra1;
    private String extra2;
    private float price;
    private int stock;
    private String image;

    public ProductModel(String id, String name, String description, String extra1, String extra2, float price, int stock, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.extra1 = extra1;
        this.extra2 = extra2;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}