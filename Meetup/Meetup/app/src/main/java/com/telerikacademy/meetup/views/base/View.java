package com.telerikacademy.meetup.views.base;

public interface View<T extends Presenter> {

    void setPresenter(T presenter);
}
