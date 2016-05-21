package com.pplnostrateam.arisyaag.insantani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.HashMap;

import javax.annotation.Nullable;

/**
 * Created by arisyaag on 5/14/16.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public static final int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "InsantaniAppPref";

    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PHONE = "phone";

    public static final String KEY_VEGETABLE_ID = "vegetableId";
    public static final String KEY_VEGETABLE_QUANTITY = "quantity";
    public static final String KEY_VEGETABLE_PRICE = "price";

    public static final String KEY_LOCATION_ADDRESS = "address";
    public static final String KEY_LOCATION_LATITUDE = "latitude";
    public static final String KEY_LOCATION_LONGITUDE = "longitude";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        FacebookSdk.sdkInitialize(context);
    }

    public void createLoginSession(long id, String name, String email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putLong(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);

        editor.commit();
    }

    public void createLoginSession(long id, String name, String email, String phone){
        editor.putBoolean(IS_LOGIN, true);
        editor.putLong(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE, phone);

        editor.commit();
    }

    public void updateUserDetails(long id, String name, String email, String phone) {
        editor.putLong(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE, phone);

        editor.commit();
    }

    public void createVegetableDetails(long id, int quantity, int price){
        editor.putLong(KEY_VEGETABLE_ID, id);
        editor.putInt(KEY_VEGETABLE_QUANTITY, quantity);
        editor.putInt(KEY_VEGETABLE_PRICE, price);

        editor.commit();
    }

    public void createVegetableDetails(long id, int price) {
        editor.putLong(KEY_VEGETABLE_ID, id);
        editor.putInt(KEY_VEGETABLE_PRICE, price);

        editor.commit();
    }

    public void updateVegetableDetails(long id, int quantity, int price){
        editor.putLong(KEY_VEGETABLE_ID, id);
        editor.putInt(KEY_VEGETABLE_QUANTITY, quantity);
        editor.putInt(KEY_VEGETABLE_PRICE, price);

        editor.commit();
    }

    public void createLocationDetails(String address, double longitude, double latitude){
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_USER_ID, Long.toString(pref.getLong(KEY_USER_ID, -1)));
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));
        user.put(KEY_USER_EMAIL, pref.getString(KEY_USER_EMAIL, null));
        user.put(KEY_USER_PHONE, pref.getString(KEY_USER_PHONE, null));

        return user;
    }

    public HashMap<String, Integer> getVegetableDetails(){
        HashMap<String, Integer> vegetable = new HashMap<>();

        vegetable.put(KEY_VEGETABLE_ID, (int)(long) pref.getLong(KEY_VEGETABLE_ID, -1));
        vegetable.put(KEY_VEGETABLE_PRICE, pref.getInt(KEY_VEGETABLE_PRICE, -1));
        vegetable.put(KEY_VEGETABLE_QUANTITY, pref.getInt(KEY_VEGETABLE_QUANTITY, -1));

        return vegetable;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, SearchingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, SearchingActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        LoginManager.getInstance().logOut();

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
