package com.jsu.bigdot.mrre.login;


import android.database.Observable;

import com.jsu.bigdot.mrre.po.User;

/**
 * login的presenter层 进行对view 和 model 的控制,
 * Created by ccj on 2016/7/7.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String username, String password) {
        loginView.showProgress();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void Change(int i) {

    }


}
