package com.example.studentmarket.Controller.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.VolleyCallback.VolleyCallback;
import com.example.studentmarket.R;
import com.example.studentmarket.Services.NotifyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotifyScreen extends AppCompatActivity {
    private ImageButton closeButton;
    private RecyclerView listNotify;
    private ArrayList<NotifyClass> arrayNotify;
    private NotifyService notifyService;
    private NotifyAdapter notifyAdapter;
    private String imgUrl="https://haycafe.vn/wp-content/uploads/2021/11/Anh-avatar-dep-chat-lam-hinh-dai-dien.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        notifyService = new NotifyService(this);

        closeButton = findViewById(R.id.notify_close_button);
        listNotify = findViewById(R.id.list_notify);

        try {
            MappingType();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void MappingType() throws JSONException {
        arrayNotify = new ArrayList<>();
        notifyService.getListNotification(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                JSONArray listNotifyRes = response.getJSONArray("notificationList");
                if (listNotifyRes.length() > 0) {
                    for (int i = 0; i < listNotifyRes.length(); i++) {
                        JSONObject object = listNotifyRes.getJSONObject(i);
                        arrayNotify.add(new NotifyClass(object.getString("userNotificationId"), "",object.getString("userNotificationBody"), object.getString("userNotificationImage"), object.getString("userNotificationTimestamp"), object.getString("userNotificationTitle"), object.getBoolean("userNotificationRead")));
                    }
                    notifyAdapter = new NotifyAdapter(arrayNotify,getApplicationContext());
                    listNotify.setAdapter(notifyAdapter);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}