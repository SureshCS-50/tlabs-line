package com.tlabs.line;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sureshkumar on 16/12/16.
 */

public class LinePreferenceManager {

    private static SharedPreferences mSharedPrefs;
    private static SharedPreferences.Editor mEditor;
    public static LinePreferenceManager instance;

    public static LinePreferenceManager getInstance(Context context){
        if(instance == null){
            instance = new LinePreferenceManager(context);
        }
        return instance;
    }

    private LinePreferenceManager(Context context) {
        mSharedPrefs = context.getSharedPreferences(Constants.LINE_PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        mEditor.putBoolean(Constants.KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return mSharedPrefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
    }

}

