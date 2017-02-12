package com.telerikacademy.meetup.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telerikacademy.meetup.utils.base.IJsonParser;

import java.lang.reflect.Type;

public class GsonParser implements IJsonParser {

    @Override
    public String toJson(Object obj) {
        return null;
    }

    @Override
    public String toJsonFromResponseBody(String responseBodyString) {
        String resultJsonFromHttp = responseBodyString;
        JsonParser jsonParser = new JsonParser();
        JsonObject rootObj = jsonParser.parse(resultJsonFromHttp).getAsJsonObject();
        String jsonObject = rootObj.getAsJsonObject("result").toString();

        return jsonObject;
    }

    @Override
    public <T> T fromJson(String json, Type classOfT) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        T result = gson.fromJson(json, classOfT);

        return result;
    }
}
