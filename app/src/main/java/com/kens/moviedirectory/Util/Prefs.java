package com.kens.moviedirectory.Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {//this will save the first search thing

    SharedPreferences sharedPreferences;

    public Prefs(Activity activity){
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void setSearch(String search){
        sharedPreferences.edit().putString("search",search).commit();
    }

    public String getSearch(){
        return sharedPreferences.getString("search", "Batman");
    }

}
