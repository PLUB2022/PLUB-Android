package com.plub.data.api

import com.plub.data.model.SampleAccountResponse
import com.plub.data.model.SampleLoginResponse
import dagger.Provides
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SampleApi {
    @GET("/api/account/check/nickname/{nickname}")
    fun checkNickname(@Path("nickname") nickname : String) : Call<SampleAccountResponse>
}