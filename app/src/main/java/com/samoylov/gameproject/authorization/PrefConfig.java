package com.samoylov.gameproject.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.samoylov.gameproject.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public void writeName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name), name);
        editor.commit();
    }

    public String readName() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "User");

    }
    public void writeCookie(String cookie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_cookie), cookie);
        editor.commit();
    }

    public String readCookie() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_cookie), "User");

    }

    public void displayToast(String massage) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
    }
}