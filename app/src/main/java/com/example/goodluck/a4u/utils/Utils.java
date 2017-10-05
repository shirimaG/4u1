package com.example.goodluck.a4u.utils;


import com.example.goodluck.a4u.data.entities.product.ProductListResponse;
import com.example.goodluck.a4u.data.entities.product.ProductsFeedDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {

    private static Gson gson;

    private Utils() {}

    /**
     * Add specific parsing gson
     *
     * @return new instance of {@link Gson}
     */
    public static Gson getGsonParser() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            gsonBuilder.registerTypeAdapter(ProductListResponse.class, new ProductsFeedDeserializer());
            gson = gsonBuilder.create();
        }
        return gson;
    }


}
