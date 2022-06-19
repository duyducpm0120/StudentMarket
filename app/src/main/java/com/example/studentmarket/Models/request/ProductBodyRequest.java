package com.example.studentmarket.Models.request;

import com.google.gson.annotations.Expose;

public class ProductBodyRequest {
    String listingTitle;
    int listingPrice;
    String listingBody;
    @Expose
    Integer[] listingCategories;

    public ProductBodyRequest(String title, int price, String body, Integer[] categories) {
        this.listingTitle = title;
        this.listingPrice = price;
        this.listingBody = body;
        this.listingCategories = categories;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public int getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(int listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getListingBody() {
        return listingBody;
    }

    public void setListingBody(String listingBody) {
        this.listingBody = listingBody;
    }



    public Integer[] getListingCategories() {
        return listingCategories;
    }

    public void setListingCategories(Integer[] listingCategories) {
        this.listingCategories = listingCategories;
    }
}
