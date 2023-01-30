package com.plub.data.api

import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitResponse
import com.plub.data.dto.applicantsrecruit.reply.ReplyApplicantsRecruitResponse
import com.plub.data.dto.applyrecruit.QuestionsResponse
import com.plub.data.dto.recruitdetail.host.EndRecruitResponse
import com.plub.data.dto.recruitdetail.host.HostApplicantsResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface RecruitApi {

    @POST(Endpoints.RECRUIT.APPLICANTS_RECRUIT)
    suspend fun applicantsRecruit(
        @Path("plubbingId") plubbingID : Int,
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

    @POST(Endpoints.RECRUIT.BOOKMARK_RECRUIT)
    suspend fun bookmarkRecruit(
        @Path("plubbingId") plubbingID: Int,
    ) : Response<ApiResponse<BookmarkResponse>>


    @PUT(Endpoints.RECRUIT.RECRUIT_END)
    suspend fun endRecruit(
        @Path("plubbingId") plubbingID: Int
    ) : Response<ApiResponse<EndRecruitResponse>>

    @GET(Endpoints.RECRUIT.APPLICANTS_RECRUIT)
    suspend fun seeApplicants(
        @Path("plubbingId") plubbingID: Int
    ) : Response<ApiResponse<HostApplicantsResponse>>

    @GET(Endpoints.RECRUIT.RECRUIT_QUESTIONS)
    suspend fun getQustions(
        @Path("plubbingId") plubbingID: Int
    ) : Response<ApiResponse<QuestionsResponse>>
}