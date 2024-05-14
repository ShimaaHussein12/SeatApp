package com.example.seats.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.seats.Model.User;
import com.example.seats.loginActivity;

public class SessionManager {

    private static final String SHERD_PREF_NAME="userToken";
    private static final String KEY_NAME="Username";
    private static final String KEY_EMAIL="UserEmail";
    private static final String KEY_TOKEN="token";
    private static final String KEY_ID="BusinessUserID";
    private static final String KEY_PASSWORD="UserPassword";
    private static final String KEY_MOBILPHONE="UserMobileNumber";
    private static SessionManager mInstance;
    private static Context mCtx;

    public SessionManager(Context context){
        mCtx=context;
    }
    public static synchronized SessionManager getInstance(Context context){
        if(mInstance==null){
           mInstance= new SessionManager(context);

        }
        return mInstance;
    }
    public  void userLogin(User user){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHERD_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_EMAIL,user.getUserEmail());
        editor.putString(KEY_NAME, user.getUsername());
        editor.putString(KEY_PASSWORD, user.getUserPassword());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_MOBILPHONE, user.getUserMobileNumber());
        editor.apply();
    }
    public  boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHERD_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN,null)!=null;

    }
    public User getToken(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHERD_PREF_NAME,Context.MODE_PRIVATE);
        return  new User(
                sharedPreferences.getString(KEY_TOKEN,null)
        );
    }
    public  void userLogout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHERD_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, loginActivity.class));
    }
    public  User getUserInfo(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHERD_PREF_NAME,Context.MODE_PRIVATE);
        return  new User(
                sharedPreferences.getString(KEY_ID,null),
                sharedPreferences.getString(KEY_NAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_MOBILPHONE,null),
                sharedPreferences.getString(KEY_TOKEN,null),
                sharedPreferences.getString(KEY_PASSWORD,null)

        );
    }
}
