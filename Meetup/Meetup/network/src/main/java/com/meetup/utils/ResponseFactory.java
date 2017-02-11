package com.meetup.utils;

import com.meetup.utils.base.IResponse;
import com.meetup.utils.base.IResponseFactory;

import java.util.List;
import java.util.Map;

public class ResponseFactory implements IResponseFactory {

    public IResponse createResponse(final Map<String, List<String>> headers, final String body,
                                    final String message, final int code) {

        return new IResponse() {
            @Override
            public Map<String, List<String>> getHeaders() {
                return headers;
            }

            @Override
            public String getBody() {
                return body;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public int getCode() {
                return code;
            }
        };
    }
}
