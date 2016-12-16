package com.tlabs.line;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Sureshkumar on 16/12/16.
 */

public class Utils {

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static <T> String convertObjectToStringJson(T someObject, Type type) {
        Gson mGson = new Gson();
        String mStrJson = mGson.toJson(someObject, type);
        return mStrJson;
    }

    public static <T> T getObjectFromJson(String json, Type type) {
        Gson mGson = new Gson();
        if (json != null) {
            if (json.isEmpty()) {
                return null;
            }
        }
        return mGson.fromJson(json, type);
    }
}
