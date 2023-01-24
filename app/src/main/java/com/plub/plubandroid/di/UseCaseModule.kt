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
    fun providesFetchPlubAccessTokenUseCase(repository: PlubJwtRepository): FetchPlubAccessTokenUseCase {
        return FetchPlubAccessTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubRefreshTokenUseCase(repository: PlubJwtRepository): FetchPlubRefreshTokenUseCase {
        return FetchPlubRefreshTokenUseCase(repository)
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
    fun providesTestPostHomeUseCase(repository: HomePostRepository): TestPostHomeUseCase {
        return TestPostHomeUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesBrowseUseCase(repository: CategoryListRepository): GetHobbiesUseCase {
        return GetHobbiesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchKakaoLocationByKeywordUseCase(repository: KakaoLocationRepository): FetchKakaoLocationByKeywordUseCase {
        return FetchKakaoLocationByKeywordUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesRecommendationGatheringUseCase(repository: RecommendationGatheringRepository): RecommendationGatheringUsecase {
        return RecommendationGatheringUsecase(repository)
    }

    @Singleton
    @Provides
    fun providesPostCreateGatheringUseCase(repository: GatheringRepository): PostCreateGatheringUseCase {
        return PostCreateGatheringUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesRecruitDetailUseCase(repository: RecruitDetailRepository): RecruitDetailUseCase {
        return RecruitDetailUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesApplicantsRecruitUseCase(repository: ApplicantsRecruitRepository): ApplicantsRecruitUseCase {
        return ApplicantsRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesReplyApplicantsRecruitUseCase(repository: ReplyApplicantsRecruitRepository): ReplyApplicantsRecruitUseCase {
        return ReplyApplicantsRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesCategoriesRecommendGatheringUseCase(repository: CategoriesGatheringRepository): GetCategoriesGatheringUseCase {
        return GetCategoriesGatheringUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesBookmarkUseCase(repository: BookmarkRepository): BookmarkUsecase {
        return BookmarkUsecase(repository)
    }

    @Singleton
    @Provides
    fun providesInterestUseCase(repository: InterestRepository): InterestUseCase {
        return InterestUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesHostEndRecruitUseCase(repository: HostRecruitRepository): HostRecruitUseCase {
        return HostRecruitUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetQuestionsUseCase(repository: RecruitApplyRepository): GetQuestionUseCase {
        return GetQuestionUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchMyInfoUseCase(repository: AccountRepository): FetchMyInfoUseCase {
        return FetchMyInfoUseCase(repository)
    }
}