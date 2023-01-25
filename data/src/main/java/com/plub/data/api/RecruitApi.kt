package com.plub.data.api

import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RecruitApi {

    @POST(Endpoints.RECRUIT.APPLICANTS_RECRUIT)
    suspend fun applicantsRecruit(
        @Path("plubbingID") plubbingID : Int,
        @Header("accessToken") accessToken : String,
        @Body request : ApplicantsRecruitRequest
    ) : Response<ApiResponse<ApplicantsRecruitResponse>>
}