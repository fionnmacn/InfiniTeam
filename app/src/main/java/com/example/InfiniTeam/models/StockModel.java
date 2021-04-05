package com.example.InfiniTeam.models;

import java.io.Serializable;

public class StockModel implements Serializable {

    private String store;
    private int stock;

    public StockModel(String store, int stock) {
        this.store = store;
        this.stock = stock;
    }
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
