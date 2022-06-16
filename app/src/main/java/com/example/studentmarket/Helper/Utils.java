package com.example.studentmarket.Helper;

import android.content.Context;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.Normalizer;
public class Utils {
    public String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
    public String getContentType (Uri uri, Context context) {
        String type = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE) > 0 ? cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE) : 1);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }
}
