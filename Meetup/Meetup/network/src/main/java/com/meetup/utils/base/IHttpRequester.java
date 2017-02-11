package com.meetup.utils.base;

import io.reactivex.Observable;

import java.util.Map;

public interface IHttpRequester {

    Observable<IHttpResponse> get(final String url);

    Observable<IHttpResponse> get(final String url, final Map<String, String> headers);

    Observable<IHttpResponse> post(final String url, final String body);

    Observable<IHttpResponse> post(final String url, final String body, final Map<String, String> headers);

    Observable<IHttpResponse> put(final String url, final String body);

    Observable<IHttpResponse> put(final String url, final String body, final Map<String, String> headers);
}
