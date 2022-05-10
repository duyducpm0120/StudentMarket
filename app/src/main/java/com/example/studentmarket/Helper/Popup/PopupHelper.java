package com.example.studentmarket.Helper.Popup;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PopupHelper {
    Context context;
    String title;
    String content;


    public PopupHelper(Context context, String title, String content) {
        this.context = context;
        this.title = title;
        this.content = content;
    }

    public void Show(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this.context);
        builder.setMessage(title);
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        TextView title = new TextView(context);
        title.setText("Thông báo");
        title.setPadding(10, 20, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(25);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        builder.setCustomTitle(title);
        AlertDialog alertDialog = builder.show();
        TextView messageText = (TextView)alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        alertDialog.show();
    }
}
