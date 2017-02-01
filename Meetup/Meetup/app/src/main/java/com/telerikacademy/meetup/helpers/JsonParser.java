package com.telerikacademy.meetup.helpers;

import com.telerikacademy.meetup.interfaces.IJsonParser;

import java.lang.reflect.Type;

public class JsonParser implements IJsonParser {

    @Override
    public String toJson(Object obj) {
        return null;
    }

    @Override
    public <T> T fromJson(String json, Type classOfT) {
        return null;
    }
}
