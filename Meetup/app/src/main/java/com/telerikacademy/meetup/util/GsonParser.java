package com.telerikacademy.meetup.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telerikacademy.meetup.util.base.IJsonParser;

import java.lang.reflect.Type;

public class GsonParser implements IJsonParser {

    @Override
    public String toJson(Object src) {
        Gson gson = new Gson();
        String json = gson.toJson(src);

        return json;
    }

    @Override
    public <T> T fromJson(String json, Type classOfT) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        T result = gson.fromJson(json, classOfT);

        return result;
    }

    @Override
    public String toJsonFromResponseBody(String responseBodyString) {
        JsonParser jsonParser = new JsonParser();
        JsonObject rootObj = jsonParser.parse(responseBodyString).getAsJsonObject();
        String jsonObject = rootObj.getAsJsonObject("result").toString();

        return jsonObject;
    }
}
