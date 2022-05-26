package com.sict.alarmsv.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sict.alarmsv.R;
import com.sict.alarmsv.activity.AlarmMainActivity;
import com.sict.alarmsv.activity.Login;

import java.util.HashMap;

public class Session {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public int PRIVATE_MODE = 0;
    public Context context;


    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN", PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email) {
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean isLoggin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLoggin()) {
            //chuyển qua trang Login
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((Login) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
        ((AlarmMainActivity) context).finish();
    }
}