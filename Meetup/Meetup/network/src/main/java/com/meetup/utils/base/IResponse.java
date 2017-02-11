package com.meetup.utils.base;

import java.util.List;
import java.util.Map;

public interface IResponse {

    Map<String, List<String>> getHeaders();

    String getBody();

    String getMessage();

    int getCode();
}
