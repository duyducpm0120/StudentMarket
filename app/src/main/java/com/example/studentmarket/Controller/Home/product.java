package com.example.studentmarket.Controller.Home;

public class product {
    private String nameProduct;
    private String priceProduct;
    private int imageProduct;
    private boolean heartProduct;

    public product(String nameProduct, String priceProduct, int imageProduct,boolean heartProduct) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.imageProduct = imageProduct;
        this.heartProduct = heartProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public int getImageProduct() {
        return imageProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }

    public boolean isHeartProduct() {
        return heartProduct;
    }

    public void setHeartProduct(boolean heartProduct) {
        this.heartProduct = heartProduct;
    }
}
