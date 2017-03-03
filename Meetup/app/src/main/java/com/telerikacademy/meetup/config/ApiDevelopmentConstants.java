package com.telerikacademy.meetup.config;

import com.telerikacademy.meetup.config.base.IApiConstants;

public final class ApiDevelopmentConstants implements IApiConstants {

    private static final String API_URL = "https://telerik-meetup.herokuapp.com";
    //    private static final String API_URL = "http://10.0.2.2:8080";
    private static final String URL_SIGN_IN = API_URL + "/auth/login";
    private static final String URL_SIGN_UP = API_URL + "/auth/register";
    private static final String URL_GET_VENUES = API_URL + "/venue";
    private static final String URL_POST_COMMENT = API_URL + "/venue/comment";
    private static final String DEFAULT_USERNAME = "anonymous";
    private static final int RESPONSE_SUCCESS_CODE = 200;
    private static final int RESPONSE_ERROR_CODE = 404;

    @Override
    public String signInUrl() {
        return URL_SIGN_IN;
    }

    @Override
    public String signUpUrl() {
        return URL_SIGN_UP;
    }

    @Override
    public String getVenueUrl() {
        return URL_GET_VENUES;
    }

    @Override
    public String postCommentUrl() {
        return URL_POST_COMMENT;
    }

    @Override
    public int responseSuccessCode() {
        return RESPONSE_SUCCESS_CODE;
    }

    @Override
    public int responseErrorCode() {
        return RESPONSE_ERROR_CODE;
    }

    @Override
    public String defaultUsername() {
        return DEFAULT_USERNAME;
    }
}
