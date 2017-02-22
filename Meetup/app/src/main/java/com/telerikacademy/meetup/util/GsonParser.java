package com.telerikacademy.meetup.util;

import com.google.gson.*;
import com.telerikacademy.meetup.util.base.IJsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    public String getDirectMember(String json, String memberName) {
        JsonParser jsonParser = new JsonParser();
        JsonObject parent = jsonParser
                .parse(json)
                .getAsJsonObject();

        JsonObject member = parent.getAsJsonObject(memberName);

        return member.toString();
    }

    @Override
    public <T> List<T> getDirectArray(String json, String memberName, Class<T> elementType) {
        JsonParser jsonParser = new JsonParser();
        JsonObject parent = jsonParser
                .parse(json)
                .getAsJsonObject();

        JsonArray memberArray = parent.getAsJsonArray(memberName);

        Gson gson = new Gson();

        List<T> list = new ArrayList<>();
        for (final JsonElement element : memberArray) {
            T entity = gson.fromJson(element, elementType);
            list.add(entity);
        }

        return list;
    }
}
