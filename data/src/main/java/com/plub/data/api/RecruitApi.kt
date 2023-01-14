package com.plub.data.api

import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitResponse
import com.plub.data.dto.plubJwt.applicantsrecruit.reply.ReplyApplicantsRecruitResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface RecruitApi {

    @POST(Endpoints.RECRUIT.APPLICANTS_RECRUIT)
    suspend fun applicantsRecruit(
        @Path("plubbingID") plubbingID : Int,
        @Body request : ApplicantsRecruitRequest
    ) : Response<ApiResponse<ApplicantsRecruitResponse>>

    @POST(Endpoints.RECRUIT.APPROVAL_APPLICANTS)
    suspend fun approvalApplicants(
        @Path("plubbingId") plubbingID: Int,
        @Path("accountId") accountId: Int
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>

    @POST(Endpoints.RECRUIT.REFUSE_APPLICANTS)
    suspend fun refuseApplicants(
        @Path("plubbingId") plubbingID: Int,
        @Path("accountId") accountId: Int
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>
}