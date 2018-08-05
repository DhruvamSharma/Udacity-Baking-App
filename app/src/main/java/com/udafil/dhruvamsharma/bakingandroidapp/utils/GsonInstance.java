package com.udafil.dhruvamsharma.bakingandroidapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

final public class GsonInstance {

    private static Gson sGson;

    private GsonInstance() {

    }

    public static Gson getGsonInstance() {
        if(sGson == null) {
            sGson = new GsonBuilder().create();
        }
        return sGson;
    }


}
