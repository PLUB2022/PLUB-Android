package com.plub.data.api

import com.plub.data.dto.kakaoLocation.KakaoLocationInfoResponse
import com.plub.data.util.KAKAO_REST_API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoLocationApi {
    @GET(Endpoints.KAKAO_LOCATION.KEYWORD_URL)
    suspend fun fetchLocationInfo(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Header("Authorization") header: String = "KakaoAK $KAKAO_REST_API_KEY"
    ): KakaoLocationInfoResponse
}