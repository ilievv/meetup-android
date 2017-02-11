package com.meetup.utils.base;

import java.util.List;
import java.util.Map;

public interface IResponseFactory {

    IResponse createResponse(final Map<String, List<String>> headers, final String body,
                             final String message, final int code);
}
