package com.example.clothesorderingapplication.data;

import com.example.clothesorderingapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Product {
    private String name;
    private String id;
    private String type;
    private String size;
    private String price;
    private String stock;
    private String description;
    private int img_id;

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

            switch (product.type) {
                case "dress":
                    product.img_id = getRandomId(new int[]{R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5});
                    break;
                case "jeans":
                    product.img_id = getRandomId(new int[]{R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6});
                    break;
                case "skirt":
                    product.img_id = getRandomId(new int[]{R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5});
                    break;
                case "t-shirt":
                    product.img_id = getRandomId(new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5, R.drawable.t6});
                    break;
                case "blouse":
                    product.img_id = getRandomId(new int[]{R.drawable.bl1, R.drawable.bl2, R.drawable.bl3, R.drawable.bl4, R.drawable.bl5, R.drawable.blouse});
                    break;
                case "jacket":
                    product.img_id = getRandomId(new int[]{R.drawable.gh1, R.drawable.gh2, R.drawable.gh3, R.drawable.gh4, R.drawable.jacket});
                    break;
                case "belt":
                    product.img_id = getRandomId(new int[]{R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6});

                    break;
                case "bag":
                    product.img_id = getRandomId(new int[]{R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.g4, R.drawable.g5, R.drawable.g6});
                    break;
                case "scarf":
                    product.img_id = getRandomId(new int[]{R.drawable.e1, R.drawable.e2, R.drawable.e3, R.drawable.e4});
                    break;
                default:
                    product.img_id = getRandomId(new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7});
                    break;
            }

            return product;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    static int getRandomId(int[] array){
        int rnd = ThreadLocalRandom.current().nextInt(0, array.length);
        return array[rnd];
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

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
