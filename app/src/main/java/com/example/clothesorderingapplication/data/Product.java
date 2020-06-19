package com.example.clothesorderingapplication.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class Product {
    private String name;
    private String id;
    private String type;
    private String size;
    private String price;
    private String stock;
    private String description;

    public static LinkedList<Product> products = new LinkedList<Product>();

    public static Product fromJSONObject(JSONObject json){

        try{
            Product product = new Product();
            product.name = json.getString("Name");
            product.id = Integer.toString(json.getInt("Id"));
            product.type = json.getString("Type");
            product.size = json.getString("Size");
            product.price = json.getString("Price");
            product.stock = json.getString("Stock");
            product.description = json.getString("Description");
            return product;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
