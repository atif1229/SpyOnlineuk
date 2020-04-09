package com.example.spyonlineuk.helpers;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spyonlineuk.models.User;
import com.google.gson.Gson;
import com.google.gson.internal.PreJava9DateFormatProvider;

import java.util.prefs.PreferenceChangeEvent;

public class SessionHelper {

    //session create
    //session destroy
    //isUserloggedin
    //take user object that logged in
    public static void createSession(Context context, User user) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("current_user", new Gson().toJson(user)).apply();
    }

    public static void logout(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove("current_user").apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        String isUser = PreferenceManager.getDefaultSharedPreferences(context).getString("current_user", null);
        return isUser != null;
    }

    public static User getCurrentUser(Context context) {
        String userObject = PreferenceManager.getDefaultSharedPreferences(context).getString("current_user", null);
        return new Gson().fromJson(userObject, User.class);
    }
}
