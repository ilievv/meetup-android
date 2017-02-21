package com.telerikacademy.meetup.config;

import com.telerikacademy.meetup.config.base.IApiConstants;

public final class ApiDevelopmentConstants implements IApiConstants {

    private static final String apiUrl = "https://telerik-meetup.herokuapp.com";
    private static final String signInUrl = apiUrl + "/auth/login";
    private static final String signUpUrl = apiUrl + "/auth/register";

    @Override
    public String signInUrl() {
        return signInUrl;
    }

    @Override
    public String signUpUrl() {
        return signUpUrl;
    }
}
