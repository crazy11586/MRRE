package com.jsu.bigdot.mrre.retrofit;

import com.jsu.bigdot.mrre.AppManager;
import com.jsu.bigdot.mrre.service.UsersService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bigdot on 2017/7/5.
 */

public class UserRetrofit {

    private Retrofit retrofit;
    private UsersService service;
    public  UsersService getService(){
        //这里的MyAPI.BaseURL是指服务器端的基本url
        //加上之前 @POST（）里的地址 组成完整的接口url
        retrofit=new Retrofit.Builder()
                .baseUrl(AppManager.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();//增加返回值为实体类的支持
        //创建service
        return retrofit.create(UsersService.class);

    }

}
