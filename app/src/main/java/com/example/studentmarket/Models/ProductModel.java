package com.example.studentmarket.Models;

import com.google.gson.annotations.SerializedName;

public class ProductModel {

    private int listingId;

    private String listingAddress;

    private String listingBody;

    private String listingImage;

    private String listingTimestamp;

    private String listingTitle;

    private int listingPrice;

    private boolean listingIsLoved = false;

    public boolean isListingIsLoved() {
        return listingIsLoved;
    }

    public void setListingIsLoved(boolean listingIsLoved) {
        this.listingIsLoved = listingIsLoved;
    }

    public ProductModel(int listingId, String listingAddress, String listingBody, String listingImage, String listingTimestamp, String listingTitle, int listingPrice) {
        this.listingId = listingId;
        this.listingAddress = listingAddress;
        this.listingBody = listingBody;
        this.listingImage = listingImage;
        this.listingTimestamp = listingTimestamp;
        this.listingTitle = listingTitle;
        this.listingPrice = listingPrice;
    }

//    public boolean getListingIsLoved() {
//        return listingIsLoved;
//    }
//
//    public void setListingIsLoved(boolean listingIsLoved) {
//        this.listingIsLoved = listingIsLoved;
//    }
// Getter Methods

    public int getListingId() {
        return listingId;
    }

    public String getListingAddress() {
        return listingAddress;
    }

    public String getListingBody() {
        return listingBody;
    }

    public String getListingImage() {
        return listingImage;
    }

    public String getListingTimestamp() {
        return listingTimestamp;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public int getListingPrice() {
        return listingPrice;
    }

    // Setter Methods

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public void setListingAddress(String listingAddress) {
        this.listingAddress = listingAddress;
    }

    public void setListingBody(String listingBody) {
        this.listingBody = listingBody;
    }

    public void setListingImage(String listingImage) {
        this.listingImage = listingImage;
    }

    public void setListingTimestamp(String listingTimestamp) {
        this.listingTimestamp = listingTimestamp;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public void setListingPrice(int listingPrice) {
        this.listingPrice = listingPrice;
    }
}
