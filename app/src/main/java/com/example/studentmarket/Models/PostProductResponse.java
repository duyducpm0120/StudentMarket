package com.example.studentmarket.Models;

import com.google.gson.annotations.SerializedName;

public class PostProductResponse {
    @SerializedName("listingId")
    private float listingId;
    @SerializedName("listingAddress")
    private String listingAddress;
    @SerializedName("listingBody")
    private String listingBody;
    @SerializedName("listingImage")
    private String listingImage;
    @SerializedName("listingTimestamp")
    private String listingTimestamp;
    @SerializedName("listingTitle")
    private String listingTitle;
    @SerializedName("listingPrice")
    private float listingPrice;


    // Getter Methods

    public float getListingId() {
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

    public float getListingPrice() {
        return listingPrice;
    }

    // Setter Methods

    public void setListingId(float listingId) {
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

    public void setListingPrice(float listingPrice) {
        this.listingPrice = listingPrice;
    }
}
