package com.example.prototype;

public class Product {
    private static String id;
    private static String name;
    private static String description;
    private static String price;

    public Product() { }

    public Product(String catNum, String name, String description, String location) {
        id = id;
        name = name;
        description = description;
        price = price;
    }

    public static String get_id() {
        return id;
    }

    public void set_catNum(String id) {
        this.id = id;
    }

    public static String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public static String get_description() {
        return description;
    }

    public void set_description(String description) {
        this.description = description;
    }

    public static String get_price() {
        return price;
    }

    public void set_location(String price) {
        this.price = price;
    }
}