package com.example.studentmarket.Models.response;

import com.example.studentmarket.Models.ProductModel;

import java.io.Serializable;
import java.util.List;

public class GetMyListingResponse  implements Serializable{
    public ProductModel[] listings;

    public GetMyListingResponse(ProductModel[] listings) {
        this.listings = listings;
    }

    public ProductModel[] getListings() {
        return listings;
    }

    public void setListings(ProductModel[] listings) {
        this.listings = listings;
    }
}
