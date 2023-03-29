package com.caudron.amusementpark.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;

public class UtilsSharedPreferences {

    public static <T extends Activity> SharedPreferences getSharedPreferencesFile(T activity, String nameFile){
        return activity.getSharedPreferences(nameFile, Context.MODE_PRIVATE);
    }

    public static <T> void saveSharedPreferences(SharedPreferences preferences, String name, ArrayList<T> datas){
        Editor editor = preferences.edit();
        String dataJson = UtilsJson.serializeObject(datas);
        editor.putString(name, dataJson);
        editor.apply();
    }

    public static <T> List<T> getSharedPreferences(SharedPreferences preferences ,String name){
        String jsonDatas = preferences.getString(name, null);
        ArrayList<T> datas = UtilsJson.deserializeListObject(jsonDatas);

        if (datas == null){
            datas = new ArrayList<>();
        }
        return datas;
    }

}
