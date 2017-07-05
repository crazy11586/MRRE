package com.jsu.bigdot.mrre.service;

import com.jsu.bigdot.mrre.bean.UserBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Bigdot on 2017/7/5.
 */

public interface UsersService {

    @POST("login")
    Call<List<UserBean>> login(@Query("userphone") String userphone,
                               @Query("password") String password);

    @POST("register")
    Call<List<UserBean>> register(@Query("userphone") String userphone,
                            @Query("username") String username,
                            @Query("password") String password,
                            @Query("userimg") String userimg);
}
