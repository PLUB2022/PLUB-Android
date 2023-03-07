package com.plub.plubandroid.di

import com.plub.domain.repository.*
import com.plub.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesPostSocialLoginUseCase(repository: LoginRepository): PostSocialLoginUseCase {
        return PostSocialLoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubAccessTokenUseCase(repository: PlubJwtRepository): GetPlubAccessTokenUseCase {
        return GetPlubAccessTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubRefreshTokenUseCase(repository: PlubJwtRepository): GetPlubRefreshTokenUseCase {
        return GetPlubRefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSavePlubAccessTokenAndRefreshTokenUseCase(repository: PlubJwtRepository): SavePlubAccessTokenAndRefreshTokenUseCase {
        return SavePlubAccessTokenAndRefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostReIssueTokenUseCase(repository: PlubJwtRepository): PostReIssueTokenUseCase {
        return PostReIssueTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetNicknameCheckUseCase(repository: SignUpRepository): GetNicknameCheckUseCase {
        return GetNicknameCheckUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetAllHobbiesUseCase(repository: HobbyRepository): GetAllHobbiesUseCase {
        return GetAllHobbiesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetSubHobbiesUseCase(repository: HobbyRepository): GetSubHobbiesUseCase {
        return GetSubHobbiesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostSignUpUseCase(repository: SignUpRepository): PostSignUpUseCase {
        return PostSignUpUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesUploadFileUseCase(repository: MediaRepository): PostUploadFileUseCase {
        return PostUploadFileUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesChangeFileUseCase(repository: MediaRepository): PostChangeFileUseCase {
        return PostChangeFileUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSetDataStoreUseCase(repository: PrefDataStoreRepository): SetDataStoreUseCase {
        return SetDataStoreUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetStringFromDataStoreUseCase(repository: PrefDataStoreRepository): GetStringFromDataStoreUseCase {
        return GetStringFromDataStoreUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchKakaoLocationByKeywordUseCase(repository: KakaoLocationRepository) : FetchKakaoLocationByKeywordUseCase {
        return FetchKakaoLocationByKeywordUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostCreateGatheringUseCase(repository: GatheringRepository): PostCreateGatheringUseCase {
        return PostCreateGatheringUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPutModifyRecruitUseCase(repository: GatheringRepository): PutModifyRecruitUseCase {
        return PutModifyRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPutModifyQuestionsUseCase(repository: RecruitRepository): PutModifyQuestionsUseCase {
        return PutModifyQuestionsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchMyInfoUseCase(repository: AccountRepository): FetchMyInfoUseCase {
        return FetchMyInfoUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchRecentSearchUseCase(repository: SearchRepository): FetchRecentSearchUseCase {
        return FetchRecentSearchUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeleteAllRecentSearchUseCase(repository: SearchRepository): DeleteAllRecentSearchUseCase {
        return DeleteAllRecentSearchUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeleteRecentSearchUseCase(repository: SearchRepository): DeleteRecentSearchUseCase {
        return DeleteRecentSearchUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesInsertRecentSearchUseCase(repository: SearchRepository): InsertRecentSearchUseCase {
        return InsertRecentSearchUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostBookmarkPlubRecruitUseCase(repository: BookmarkRepository): PostBookmarkPlubRecruitUseCase {
        return PostBookmarkPlubRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetMyPlubBookmarksUseCase(repository: BookmarkRepository): GetMyPlubBookmarksUseCase {
        return GetMyPlubBookmarksUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubingMainUseCase(repository: PlubingMainRepository): FetchPlubingMainUseCase {
        return FetchPlubingMainUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostAdminLoginUseCase(repository: LoginRepository): PostAdminLoginUseCase {
        return PostAdminLoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubingBoardUseCase(repository: PlubingBoardRepository): GetBoardFeedsUseCase {
        return GetBoardFeedsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubingPinsUseCase(repository: PlubingBoardRepository): GetBoardPinsUseCase {
        return GetBoardPinsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPutPlubingBoardPinChangeUseCase(repository: PlubingBoardRepository): PutBoardChangePinUseCase {
        return PutBoardChangePinUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeletePlubingBoardUseCase(repository: PlubingBoardRepository): DeleteBoardUseCase {
        return DeleteBoardUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostPlubingBoardUseCase(repository: PlubingBoardRepository): PostBoardCreateUseCase {
        return PostBoardCreateUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostCommentUseCase(repository: PlubingBoardRepository): PostBoardCommentCreateUseCase {
        return PostBoardCommentCreateUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetCommentListUseCase(repository: PlubingBoardRepository): GetBoardCommentsUseCase {
        return GetBoardCommentsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetBoardDetailUseCase(repository: PlubingBoardRepository): GetBoardDetailUseCase {
        return GetBoardDetailUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPutEditBoardUseCase(repository: PlubingBoardRepository): PutBoardEditUseCase {
        return PutBoardEditUseCase(repository)
    }


    @Singleton
    @Provides
    fun providesGetCategoriesUseCase(repository: CategoryListRepository): GetCategoriesUseCase  {
        return GetCategoriesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetRecommendationGatheringUseCase(repository: RecommendationGatheringRepository): GetRecommendationGatheringUsecase {
        return GetRecommendationGatheringUsecase(repository)
    }

    @Singleton
    @Provides
    fun providesGetRecruitDetailUseCase(repository: RecruitRepository): GetRecruitDetailUseCase {
        return GetRecruitDetailUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostApplyRecruitUseCase(repository: RecruitRepository): PostApplyRecruitUseCase {
        return PostApplyRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostApprovalApplicantsRecruitUseCase(repository: ReplyApplicantsRecruitRepository): PostApprovalApplicantsRecruitUseCase {
        return PostApprovalApplicantsRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetCategoriesGatheringUseCase(repository: RecommendationGatheringRepository): GetCategoriesGatheringUseCase {
        return GetCategoriesGatheringUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostRegisterHobbiesUseCase(repository: RegisterHobbiesRepository): PostRegisterHobbiesUseCase {
        return PostRegisterHobbiesUseCase(repository)
    }
    @Singleton
    @Provides
    fun providesGetRecruitApplicantsUseCase(repository: ApplicantsRepository): GetRecruitApplicantsUseCase {
        return GetRecruitApplicantsUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetQuestionsUseCase(repository: RecruitRepository): GetRecruitQuestionUseCase {
        return GetRecruitQuestionUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPutEndRecruitUseCase(repository: RecruitRepository): PutEndRecruitUseCase {
        return PutEndRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostRefuseApplicantsRecruitUseCase(repository: ReplyApplicantsRecruitRepository): PostRefuseApplicantsRecruitUseCase {
        return PostRefuseApplicantsRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetMyInterestUseCase(repository: RegisterHobbiesRepository): GetMyInterestUseCase {
        return GetMyInterestUseCase(repository)
    }
}