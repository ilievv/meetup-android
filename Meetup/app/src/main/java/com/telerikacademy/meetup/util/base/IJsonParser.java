package com.telerikacademy.meetup.util.base;

import java.lang.reflect.Type;

public interface IJsonParser {

    String toJson(Object obj);

    <T> T fromJson(String json, Type classOfT);

    String toJsonFromResponseBody(String responseBodyString);
}
