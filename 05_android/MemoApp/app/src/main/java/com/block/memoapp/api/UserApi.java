package com.block.memoapp.api;

import com.block.memoapp.model.User;
import com.block.memoapp.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    // 회원가입 API
    @POST("/user/register")
    Call<UserRes> register(@Body User user);

}
