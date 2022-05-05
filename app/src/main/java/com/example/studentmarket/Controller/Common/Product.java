package com.example.studentmarket.Controller.Common;

public class Product {
    private String name;
    private String price;
    private int image;
    private boolean heart;

    public Product(String nameProduct, String priceProduct, int imageProduct, boolean heartProduct) {
        this.name = nameProduct;
        this.price = priceProduct;
        this.image = imageProduct;
        this.heart = heartProduct;
    }

    public String getNameProduct() {
        return name;
    }

    public String getPriceProduct() {
        return price;
    }

    public int getImageProduct() {
        return image;
    }

    public void setNameProduct(String nameProduct) {
        this.name = nameProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.price = priceProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.image = imageProduct;
    }

    public boolean isHeartProduct() {
        return heart;
    }

    public void setHeartProduct(boolean heartProduct) {
        this.heart = heartProduct;
    }
}
