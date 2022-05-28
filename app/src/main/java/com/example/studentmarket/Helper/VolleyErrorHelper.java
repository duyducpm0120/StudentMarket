package com.example.studentmarket.Helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.studentmarket.Helper.Popup.PopupHelper;
import com.example.studentmarket.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyErrorHelper {
    private Context context;

    public VolleyErrorHelper(Context context) {
        this.context = context;
    }

    //    /**
//     * Returns appropriate message which is to be displayed to the user
//     * against the specified error object.
//     *
//     * @param error
//     * @param context
//     * @return
//     */
//
//    public static String getMessage (Object error , Context context){
//        if(error instanceof TimeoutError){
//            return "Timeout";
//        }else if (isServerProblem(error)){
//            return handleServerError(error ,context);
//        }else if(isNetworkProblem(error)){
//            return "No Internet";
//        }
//        return "Generic Error";
//
//    }
//
//    private static String handleServerError(Object error, Context context) {
//
//        VolleyError er = (VolleyError)error;
//        NetworkResponse response = er.networkResponse;
//        if(response != null){
//            switch (response.statusCode){
//
//                case 404:
//                case 422:
//                case 401:
//                    try {
//                        // server might return error like this { "error": "Some error occured" }
//                        // Use "Gson" to parse the result
//                        HashMap<String, String> result = new Gson().fromJson(new String(response.data),
//                                new TypeToken<Map<String, String>>() {
//                                }.getType());
//
//                        if (result != null && result.containsKey("error")) {
//                            return result.get("error");
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // invalid request
//                    return ((VolleyError) error).getMessage();
//
//                default:
//                    return "Timeout";
//            }
//        }
//
//        return "Generic Error";
//    }
//
//    private static boolean isServerProblem(Object error) {
//        return (error instanceof ServerError || error instanceof AuthFailureError);
//    }
//
//    private static boolean isNetworkProblem (Object error){
//        return (error instanceof NetworkError || error instanceof NoConnectionError);
//    }
    public void showVolleyError(VolleyError error, String errorTitle) {
        try {
//            String responseBody = new String(error.networkResponse.data, "utf-8");
//            JSONObject data = new JSONObject(responseBody);
//            JSONArray errors = data.getJSONArray("errors");
//            JSONObject jsonMessage = errors.getJSONObject(0);
//            String message = jsonMessage.getString("message");
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            String message = data.optString("message");
            Log.d("parse err ", message);
            PopupHelper popup = new PopupHelper(context, "Thông Báo", errorTitle);
            popup.Show();
        } catch (JSONException e) {
            Log.d("JSONException err", e.toString());
            PopupHelper popup = new PopupHelper(context, context.getString(R.string.error), "aaa");
            popup.Show();
        } catch (UnsupportedEncodingException err) {
            Log.d("UnsupportedEncodingException err", err.toString());
            PopupHelper popup = new PopupHelper(context, context.getString(R.string.error), "aaa");
            popup.Show();
        } catch (Exception err) {
            Log.d("Other Exception err", err.toString());
            PopupHelper popup = new PopupHelper(context, context.getString(R.string.error), "aaa");
            popup.Show();
        }
    }
}
