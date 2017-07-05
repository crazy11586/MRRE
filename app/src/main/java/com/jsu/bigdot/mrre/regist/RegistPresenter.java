package com.jsu.bigdot.mrre.regist;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.bean.UserBean;
import com.jsu.bigdot.mrre.regist.fragment.RegisterFirstStepFragment;
import com.jsu.bigdot.mrre.regist.fragment.RegisterSecondStepFragment;
import com.jsu.bigdot.mrre.regist.fragment.RegisterThirdStepFragment;
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
public class RegistPresenter implements RegistContract.Presenter {

    private RegistContract.View loginView;
    FragmentTransaction mFragmentTransaction;

    RegisterFirstStepFragment fragment_1;
    RegisterSecondStepFragment fragment_2;
    RegisterThirdStepFragment fragment_3;

    public RegistPresenter(RegistContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }

    private Call<List<UserBean>> callback;
    private UserBean studentBean;
    private UsersService service;

    @Override
    public void register(String userphone , String username , String password , String userimg) {
        service=new UserRetrofit().getService();
        callback = service.register(userphone, username,password,userimg);

        callback.enqueue(new Callback<List<UserBean>>() {
            @Override
            public void onResponse(Call<List<UserBean>> call, Response<List<UserBean>> response) {
                Log.i("isSuccess","true");
                List<UserBean> studentBean = response.body();
                loginView.hideProgress();
                loginView.PasswordOk();
            }

            @Override
            public void onFailure (Call <List<UserBean>> call, Throwable t){
                loginView.hideProgress();
                loginView.PasswordError();
            }
        });
        loginView.showProgress();

    }

    @Override
    public void setFragmentTransaction(FragmentTransaction fragmentTransaction) {
        mFragmentTransaction = fragmentTransaction;
    }


    public void Change(int index) {
        switch(index){
            case 1: ShowFirstFragment();break;
            case 2: ShowSecondFragment();break;
            case 3: ShowThirdFragment();break;
        }
    }

    public void init(){
        ShowFirstFragment();
    }

    private void ShowFirstFragment(){
        if(fragment_1 == null){
            fragment_1 = new RegisterFirstStepFragment();
            mFragmentTransaction.add(R.id.fragment_main, fragment_1).commit();
        }
        //隐藏所有fragment
        hideFragment(mFragmentTransaction);
        //显示需要显示的fragment
        mFragmentTransaction.show(fragment_1);
    }


    private void ShowSecondFragment(){
        if(fragment_2 == null){
            fragment_2 = new RegisterSecondStepFragment();
            mFragmentTransaction.add(R.id.fragment_main, fragment_2).commit();
        }
        //隐藏所有fragment
        hideFragment(mFragmentTransaction);
        //显示需要显示的fragment
        mFragmentTransaction.show(fragment_2);
    }

    private void ShowThirdFragment(){
        if(fragment_3 == null){
            fragment_3 = new RegisterThirdStepFragment();
            mFragmentTransaction.add(R.id.fragment_main, fragment_3).commit();
        }
        //隐藏所有fragment
        hideFragment(mFragmentTransaction);
        //显示需要显示的fragment
        mFragmentTransaction.show(fragment_3);
    }

    private void hideFragment(FragmentTransaction transaction){
        if(fragment_1 != null){
            transaction.hide(fragment_1);
        }
        if(fragment_2 != null){
            transaction.hide(fragment_2);
        }
        if(fragment_3 != null){
            transaction.hide(fragment_3);
        }
    }

}
