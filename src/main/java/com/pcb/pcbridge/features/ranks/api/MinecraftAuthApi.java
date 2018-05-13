package com.pcb.pcbridge.features.ranks.api;

import com.pcb.pcbridge.archived.MinecraftAuthBaseResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MinecraftAuthApi {

    @FormUrlEncoded
    @POST("minecraft/authenticate")
    Call<MinecraftAuthBaseResponse> login(
        @Field("email")     String email,
        @Field("password")  String password
    );

}
