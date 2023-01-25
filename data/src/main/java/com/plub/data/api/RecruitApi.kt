package com.plub.data.api

import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitResponse
import com.plub.data.dto.applicantsrecruit.reply.ReplyApplicantsRecruitResponse
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

    @POST(Endpoints.RECRUIT.APPROVAL_APPLICANTS)
    suspend fun approvalApplicants(
        @Path("plubbingId") plubbingID: Int,
        @Path("accountId") accountId: Int,
        @Header("accessToken") accessToken: String
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>

    @POST(Endpoints.RECRUIT.REFUSE_APPLICANTS)
    suspend fun refuseApplicants(
        @Path("plubbingId") plubbingID: Int,
        @Path("accountId") accountId: Int,
        @Header("accessToken") accessToken: String
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>
}