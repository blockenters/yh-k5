package com.block.postingapp.api;

import com.block.postingapp.model.Res;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LikeApi {

    // 좋아요 API
    @POST("/like/{postingId}")
    Call<Res> setLike(@Path("postingId") int postingId,
                      @Header("Authorization") String token);

    // 좋아요 해지 API
    @DELETE("/like/{postingId}")
    Call<Res> deleteLike(@Path("postingId") int postingId,
                      @Header("Authorization") String token);

}
