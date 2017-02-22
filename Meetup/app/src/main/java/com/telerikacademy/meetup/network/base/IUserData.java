package com.telerikacademy.meetup.network.base;

import com.telerikacademy.meetup.model.base.IUser;
import io.reactivex.Observable;

public interface IUserData {

    Observable<IUser> signIn(String username, String password);

    Observable<IUser> signUp(String username, String password);
}
