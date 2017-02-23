package com.telerikacademy.meetup.network;

import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.model.base.IUser;
import com.telerikacademy.meetup.network.base.IUserData;
import com.telerikacademy.meetup.util.base.*;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserData implements IUserData {

    private final IApiConstants apiConstants;
    private final IHttpRequester httpRequester;
    private final IJsonParser jsonParser;
    private final IUserSession userSession;
    private final IHashProvider hashProvider;
    private final Type userModelType;

    @Inject
    public UserData(IApiConstants apiConstants, IHttpRequester httpRequester,
                    IJsonParser jsonParser, IUserSession userSession,
                    IHashProvider hashProvider, Type userModelType) {

        this.apiConstants = apiConstants;
        this.httpRequester = httpRequester;
        this.jsonParser = jsonParser;
        this.userSession = userSession;
        this.hashProvider = hashProvider;
        this.userModelType = userModelType;
    }

    @Override
    public Observable<IUser> signIn(String username, String password) {
        Map<String, String> userCredentials = new HashMap<>();
        String passHash = hashProvider.hashPassword(password);
        userCredentials.put("username", username);
        userCredentials.put("passHash", passHash);

        return httpRequester
                .post(apiConstants.signInUrl(), userCredentials)
                .map(new Function<IHttpResponse, IUser>() {
                    @Override
                    public IUser apply(IHttpResponse iHttpResponse) throws Exception {

                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody().toString();
                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        IUser resultUser = jsonParser.fromJson(userJson, userModelType);

                        userSession.setUsername(resultUser.getUsername());
                        userSession.setId(resultUser.getId());

                        return resultUser;
                    }
                });
    }

    @Override
    public Observable<IUser> signUp(String username, String password) {
        Map<String, String> userCredentials = new HashMap<>();
        String passHash = hashProvider.hashPassword(password);
        userCredentials.put("username", username);
        userCredentials.put("passHash", passHash);

        return httpRequester
                .post(apiConstants.signUpUrl(), userCredentials)
                .map(new Function<IHttpResponse, IUser>() {
                    @Override
                    public IUser apply(IHttpResponse iHttpResponse) throws Exception {

                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody().toString();
                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        IUser resultUser = jsonParser.fromJson(userJson, userModelType);

                        return resultUser;
                    }
                });
    }
}
