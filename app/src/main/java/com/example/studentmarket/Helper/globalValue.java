package com.example.studentmarket.Helper;

import com.example.studentmarket.Controller.Common.Product;

import java.util.ArrayList;

public class globalValue {
    private static int Index;
    private static ArrayList<Product> listProduct;
    private static String username;
    private static String token;
    private static boolean firstAcess=true;

    public static boolean isFirstAcess() {
        return firstAcess;
    }

    public static void setFirstAcess(boolean firstAcess) {
        globalValue.firstAcess = firstAcess;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        globalValue.username = username;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        globalValue.token = token;
    }

    public static int getIndex() {
        return Index;
    }

    public static void setIndex(int index) {
        Index = index;
    }

    public static ArrayList<Product> getListProduct() {
        return listProduct;
    }

    public static void setListProduct(ArrayList<Product> listProduct) {
        globalValue.listProduct = listProduct;
    }
}
