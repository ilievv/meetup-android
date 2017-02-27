package com.telerikacademy.meetup.config;

import com.telerikacademy.meetup.config.base.IApiConstants;

public final class ApiDevelopmentConstants implements IApiConstants {

    private static final String apiUrl = "https://telerik-meetup.herokuapp.com";
    //private static final String apiUrl = "http://10.0.2.2:8080";
    private static final String signInUrl = apiUrl + "/auth/login";
    private static final String signUpUrl = apiUrl + "/auth/register";
    private static final int responseSuccessCode = 200;
    private static final int responseErrorCode = 404;
    private static final String defaultUsername = "anonymous";
    private static final int recentVenuesCount = 6;

    @Override
    public String signInUrl() {
        return signInUrl;
    }

    @Override
    public String signUpUrl() {
        return signUpUrl;
    }

    @Override
    public int responseSuccesCode() {
        return responseSuccessCode;
    }

    @Override
    public int responseErrorCode() {
        return responseErrorCode;
    }

    @Override
    public String defaultUsername() {
        return defaultUsername;
    }

    @Override
    public int recentVenuesForDisplayCount() {
        return recentVenuesCount;
    }
}
