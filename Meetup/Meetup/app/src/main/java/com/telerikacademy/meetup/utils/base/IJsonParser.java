package com.telerikacademy.meetup.utils.base;

import java.lang.reflect.Type;

public interface IJsonParser {

    String toJson(Object obj);

    <T> T fromJson(String json, Type classOfT);
}
