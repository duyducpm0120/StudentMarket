package com.example.studentmarket.Helper.Popup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.studentmarket.Controller.Common.Product;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import static com.example.studentmarket.Helper.globalValue.*;

import java.util.ArrayList;

public class PopupHelper {
    Context context;
    String title = "Thông báo";
    String content;
    boolean isHasCancelButton = false;
    String titleNegative = "Huỷ";
    String titlePositive = "Đồng ý";
    int id;
    PopupHelperAction popupHelperAction = null;

    public PopupHelper(Context context, String title, String content) {
        this.context = context;
        this.title = title;
        this.content = content;
    }


    public PopupHelper(Context context, String title, String content, boolean isHasCancelButton) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.isHasCancelButton = isHasCancelButton;
    }

    public PopupHelper(Context context, int id, String title, String content, boolean isHasCancelButton, @Nullable String titleNegative, @Nullable String titlePositive) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.isHasCancelButton = isHasCancelButton;
        this.titleNegative = titleNegative;
        this.titlePositive = titlePositive;
        this.id = id;
    }

    public PopupHelper(Context context, int id, String title, String content, boolean isHasCancelButton, @Nullable String titleNegative, @Nullable String titlePositive, PopupHelperAction popupHelperAction) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.isHasCancelButton = isHasCancelButton;
        this.titleNegative = titleNegative;
        this.titlePositive = titlePositive;
        this.id = id;
        this.popupHelperAction = popupHelperAction;
    }


    public void Show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this.context);
        builder.setMessage(title);
        builder.setPositiveButton(titlePositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isHasCancelButton) {
                    if (popupHelperAction != null)
                        popupHelperAction.onAction();
                }
            }
        });
        if (isHasCancelButton) {
            builder.setNegativeButton(titleNegative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    if (popupHelperAction != null)
                        popupHelperAction.onClose();
                }
            });
        }
        TextView title = new TextView(context);
        title.setText(this.title);
        title.setPadding(10, 20, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(25);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        builder.setCustomTitle(title);
        AlertDialog alertDialog = builder.show();
        TextView messageText = (TextView) alertDialog.findViewById(android.R.id.message);
        messageText.setText(this.content);
        messageText.setGravity(Gravity.CENTER);
        alertDialog.show();
    }
}
