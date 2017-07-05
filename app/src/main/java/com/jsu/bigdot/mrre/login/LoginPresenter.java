package com.jsu.bigdot.mrre.login;


import android.database.Observable;
import android.util.Log;

import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.bean.UserBean;
import com.jsu.bigdot.mrre.retrofit.UserRetrofit;
import com.jsu.bigdot.mrre.service.UsersService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * login的presenter层 进行对view 和 model 的控制,
 * Created by ccj on 2016/7/7.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    private Call<List<UserBean>> callback;
    private UserBean studentBean;
    private UsersService service;
    @Override
    public void login(String userphone, String password) {
        service=new UserRetrofit().getService();
        callback = service.login(userphone, password);

        Log.d("test", "login: "+userphone+" "+password);
        callback.enqueue(new Callback<List<UserBean>>() {
            @Override
            public void onResponse(Call<List<UserBean>> call, Response<List<UserBean>> response) {
                Log.i("isSuccess","true");
                List<UserBean> studentBean = response.body();
                loginView.hideProgress();

                loginView.PasswordOK();
            }

            @Override
            public void onFailure (Call <List<UserBean>> call, Throwable t){
                loginView.hideProgress();
                loginView.PasswordError();
                String s = t.getMessage();
                Log.d("test", "onFailure: "+s);
            }
        });
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
