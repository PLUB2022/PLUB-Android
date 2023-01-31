package com.plub.data.api

import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitResponse
import com.plub.data.dto.applicantsrecruit.reply.ReplyApplicantsRecruitResponse
import com.plub.data.dto.applyrecruit.QuestionsResponse
import com.plub.data.dto.bookmark.PlubBookmarkResponse
import com.plub.data.dto.recruitdetail.host.EndRecruitResponse
import com.plub.data.dto.recruitdetail.host.HostApplicantsResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface RecruitApi {

    @POST(Endpoints.PLUBBING.APPLICANTS_RECRUIT)
    suspend fun applicantsRecruit(
        @Path("plubbingId") plubbingID : Int,
        @Body request : ApplicantsRecruitRequest
    ) : Response<ApiResponse<ApplicantsRecruitResponse>>

    @POST(Endpoints.PLUBBING.APPROVAL_APPLICANTS)
    suspend fun approvalApplicants(
        @Path("plubbingId") plubbingID: Int,
        @Path("accountId") accountId: Int
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>

    @POST(Endpoints.PLUBBING.REFUSE_APPLICANTS)
    suspend fun refuseApplicants(
        @Path("plubbingId") plubbingID: Int,
        @Path("accountId") accountId: Int
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>

    @PUT(Endpoints.PLUBBING.RECRUIT_END)
    suspend fun endRecruit(
        @Path("plubbingId") plubbingID: Int
    ) : Response<ApiResponse<EndRecruitResponse>>

    @GET(Endpoints.PLUBBING.APPLICANTS_RECRUIT)
    suspend fun seeApplicants(
        @Path("plubbingId") plubbingID: Int
    ) : Response<ApiResponse<HostApplicantsResponse>>

    @GET(Endpoints.PLUBBING.RECRUIT_QUESTIONS)
    suspend fun getQustions(
        @Path("plubbingId") plubbingID: Int
    ) : Response<ApiResponse<QuestionsResponse>>
}