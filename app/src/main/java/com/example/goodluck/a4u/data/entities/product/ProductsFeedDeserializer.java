package com.example.goodluck.a4u.data.entities.product;


import com.example.goodluck.a4u.utils.Utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductsFeedDeserializer implements JsonDeserializer<ProductListResponse> {

    private static final String COUNT = "count";
    private static final String NEXT = "next";
    private static final String PREVIOUS = "previous";
    private static final String RESULTS = "results";

    private static String getNullOrString(JsonElement jsonElement) {
        return jsonElement.isJsonNull() ? null : jsonElement.getAsString();
    }

    @Override
    public ProductListResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        // Products feed nodes
        final JsonElement jsonCount = jsonObject.get(COUNT);
        final JsonElement jsonNext = jsonObject.get(NEXT);
        final JsonElement jsonPrevious = jsonObject.get(PREVIOUS);
        final JsonElement jsonProducts = jsonObject.get(RESULTS);

        // POJO
        final long count = jsonCount.getAsLong();
        final String next = getNullOrString(jsonNext);
        final String previous = getNullOrString(jsonPrevious);

        Type productsListType = new TypeToken<ArrayList<Product>>(){}.getType();
        final List<Product> products = Utils.getGsonParser().fromJson(jsonProducts.getAsJsonArray(), productsListType);

        final ProductListResponse response = new ProductListResponse();
        response.setCount(count);
        response.setNext(next);
        response.setPrevious(previous);
        response.setProducts(products);

        return response;
    }
}
