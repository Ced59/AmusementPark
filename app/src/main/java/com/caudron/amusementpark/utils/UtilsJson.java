package com.caudron.amusementpark.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UtilsJson {
    public static <T> String serializeObject(T obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> ArrayList<T> deserializeListObject(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {}.getType();
        return gson.fromJson(json, type);
    }
}
