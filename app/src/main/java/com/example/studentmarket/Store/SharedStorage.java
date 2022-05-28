package com.example.studentmarket.Store;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedStorage {
    Context context;

    public SharedStorage(Context context) {
        this.context = context;
    }

    public void saveValue(String key, String value) {
        Log.d("Local key saved", value);
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(key, value);
        myEdit.commit();
    }

    public String getValue(String key) {
        SharedPreferences sh = context.getSharedPreferences(key, MODE_PRIVATE);
        String value = sh.getString(key, "");
        return value;
    }

    public void removeValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.remove(key);
        myEdit.commit();
    }
}
