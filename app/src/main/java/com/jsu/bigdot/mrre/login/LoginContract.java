package com.jsu.bigdot.mrre.login;


import com.jsu.bigdot.mrre.base.BasePresenter;
import com.jsu.bigdot.mrre.base.BaseView;

/**
 * 登录关联接口类
 *
 * Created by Administrator on 2016/7/7.
 */
public interface LoginContract {


    interface View extends BaseView {

        void showProgress();
        void hideProgress();
        void navigateToRegister();
        void PasswordError();
        void PasswordOK();
    }

    interface Presenter extends BasePresenter {

        void login(String username, String password);
        void onDestroy();

    }

    interface Model{

    }

}
