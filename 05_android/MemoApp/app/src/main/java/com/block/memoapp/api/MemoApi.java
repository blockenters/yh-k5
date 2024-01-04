package com.block.memoapp.api;

import com.block.memoapp.model.Memo;
import com.block.memoapp.model.Res;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MemoApi {

    // 메모 생성 API
    @POST("/memo")
    Call<Res> addMemo(@Header("Authorization") String token, @Body Memo memo);

}
