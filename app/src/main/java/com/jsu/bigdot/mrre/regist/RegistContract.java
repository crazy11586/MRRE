package com.jsu.bigdot.mrre.regist;


import android.support.v4.app.FragmentTransaction;

import com.jsu.bigdot.mrre.base.BasePresenter;
import com.jsu.bigdot.mrre.base.BaseView;

/**
 * Created by Bigdot on 2017/7/3.
 */

public interface RegistContract {

    interface View extends BaseView{
        void showProgress();
        void hideProgress();
        void PasswordError();
        void PasswordOk();
    }

    interface Presenter extends BasePresenter{
        void register(String userphone , String username , String password , String userimg);

        void setFragmentTransaction(FragmentTransaction fragmentTransaction);

        void init();
        void Change(int index);
    }

    interface Model {

    }
}
