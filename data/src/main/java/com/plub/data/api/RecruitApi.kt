package com.plub.data.api

import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitRequest
import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitResponse
import com.plub.data.dto.applicantsRecruit.reply.ReplyApplicantsRecruitResponse
import com.plub.data.dto.applyRecruit.QuestionsListResponse
import com.plub.data.dto.recruitDetail.host.EndRecruitResponse
import com.plub.data.base.ApiResponse
import com.plub.data.base.DataDto
import com.plub.data.dto.applyRecruit.ModifyApplicationResponse
import com.plub.data.dto.createGathering.CreateGatheringResponse
import com.plub.data.dto.modifyGathering.ModifyQuestionRequestBody
import com.plub.data.dto.myPage.MyApplicationDeleteResponse
import com.plub.data.dto.recruitDetail.RecruitDetailResponse
import com.plub.data.dto.recruitDetail.host.HostApplicantsListResponse
import retrofit2.Response
import retrofit2.http.*

interface RecruitApi {
    companion object{
        private const val PATH_PLUBBING_ID = "plubbingId"
        private const val PATH_ACCOUNT_ID = "accountId"
    }

    @GET(Endpoints.PLUBBING.FETCH_DETAIL_RECRUIT)
    suspend fun fetchRecruitDetail(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
    ) : Response<ApiResponse<RecruitDetailResponse>>

    @POST(Endpoints.PLUBBING.APPLICANTS_RECRUIT)
    suspend fun applicantsRecruit(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Body request : ApplicantsRecruitRequest
    ) : Response<ApiResponse<ApplicantsRecruitResponse>>

    @POST(Endpoints.PLUBBING.APPROVAL_APPLICANTS)
    suspend fun approvalApplicants(
        @Path(PATH_PLUBBING_ID) plubbingId: Int,
        @Path(PATH_ACCOUNT_ID) accountId: Int
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>

    @POST(Endpoints.PLUBBING.REFUSE_APPLICANTS)
    suspend fun refuseApplicants(
        @Path(PATH_PLUBBING_ID) plubbingId: Int,
        @Path(PATH_ACCOUNT_ID) accountId: Int
    ) : Response<ApiResponse<ReplyApplicantsRecruitResponse>>

    @PUT(Endpoints.PLUBBING.RECRUIT_END)
    suspend fun endRecruit(
        @Path(PATH_PLUBBING_ID) plubbingId: Int
    ) : Response<ApiResponse<EndRecruitResponse>>

    @GET(Endpoints.PLUBBING.APPLICANTS_RECRUIT)
    suspend fun seeApplicants(
        @Path(PATH_PLUBBING_ID) plubbingId: Int
    ) : Response<ApiResponse<HostApplicantsListResponse>>

    @GET(Endpoints.PLUBBING.RECRUIT_QUESTIONS)
    suspend fun getQustions(
        @Path(PATH_PLUBBING_ID) plubbingId: Int
    ) : Response<ApiResponse<QuestionsListResponse>>

    @PUT(Endpoints.PLUBBING.RECRUIT_QUESTIONS)
    suspend fun modifyQuestions(
        @Path(PATH_PLUBBING_ID) plubbingId: Int,
        @Body request: ModifyQuestionRequestBody
    ) : Response<ApiResponse<CreateGatheringResponse>>

    @DELETE(Endpoints.PLUBBING.CANCEL_APPLICATION)
    suspend fun deleteMyApplication(
        @Path(PATH_PLUBBING_ID) plubbingId : Int
    ) : Response<ApiResponse<MyApplicationDeleteResponse>>

    @PUT(Endpoints.PLUBBING.MODIFY_GATHERING_RECRUIT)
    suspend fun modifyMyApplication(
        @Path(PATH_PLUBBING_ID) plubbingId : Int,
        @Body request : ApplicantsRecruitRequest
    ) : Response<ApiResponse<DataDto.DTO>>
}