package com.telerikacademy.meetup.utils;

import com.telerikacademy.meetup.utils.base.IJsonParser;

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
