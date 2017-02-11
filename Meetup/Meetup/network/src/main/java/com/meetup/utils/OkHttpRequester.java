package com.meetup.utils;

import com.meetup.utils.base.IRequester;
import com.meetup.utils.base.IResponse;
import com.meetup.utils.base.IResponseFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.*;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.Callable;

public class OkHttpRequester implements IRequester {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final IResponseFactory responseFactory;

    private final OkHttpClient httpClient;

    @Inject
    public OkHttpRequester(IResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
        this.httpClient = new OkHttpClient();
    }

    public Observable<IResponse> get(final String url) {
        return Observable.defer(new Callable<ObservableSource<? extends IResponse>>() {
            @Override
            public ObservableSource<? extends IResponse> call() throws Exception {
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = httpClient.newCall(request).execute();

                    IResponse responseParsed = responseFactory.createResponse(
                            response.headers().toMultimap(), response.body().string(),
                            response.message(), response.code());

                    return Observable.just(responseParsed);
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }

    public Observable<IResponse> get(final String url, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends IResponse>>() {
            @Override
            public ObservableSource<? extends IResponse> call() throws Exception {
                try {
                    Request.Builder requestBuilder = new Request.Builder()
                            .url(url);

                    for (Map.Entry<String, String> pair : headers.entrySet()) {
                        requestBuilder.addHeader(pair.getKey(), pair.getValue());
                    }

                    Request request = requestBuilder.build();

                    Response response = httpClient.newCall(request).execute();

                    IResponse responseParsed = responseFactory.createResponse(
                            response.headers().toMultimap(), response.body().string(),
                            response.message(), response.code());

                    return Observable.just(responseParsed);
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }

    public Observable<IResponse> post(final String url, final String body) {
        return Observable.defer(new Callable<ObservableSource<? extends IResponse>>() {
            @Override
            public ObservableSource<? extends IResponse> call() throws Exception {
                try {
                    RequestBody requestBody = RequestBody.create(JSON, body);

                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();

                    Response response = httpClient.newCall(request).execute();

                    IResponse responseParsed = responseFactory.createResponse(
                            response.headers().toMultimap(), response.body().string(),
                            response.message(), response.code());

                    return Observable.just(responseParsed);
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }

    public Observable<IResponse> post(final String url, final String body, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends IResponse>>() {
            @Override
            public ObservableSource<? extends IResponse> call() throws Exception {
                try {
                    RequestBody requestBody = RequestBody.create(JSON, body);

                    Request.Builder requestBuilder = new Request.Builder()
                            .url(url)
                            .post(requestBody);

                    for (Map.Entry<String, String> pair : headers.entrySet()) {
                        requestBuilder.addHeader(pair.getKey(), pair.getValue());
                    }

                    Request request = requestBuilder.build();

                    Response response = httpClient.newCall(request).execute();

                    IResponse responseParsed = responseFactory.createResponse(
                            response.headers().toMultimap(), response.body().string(),
                            response.message(), response.code());

                    return Observable.just(responseParsed);
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
