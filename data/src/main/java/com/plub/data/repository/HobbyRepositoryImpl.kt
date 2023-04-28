package com.plub.data.repository

import com.plub.data.api.HobbyApi
import com.plub.data.api.HomeApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.registerHobbies.RegisterHobbiesRequest
import com.plub.data.mapper.AllHobbiesResponseMapper
import com.plub.data.mapper.CategoryListResponseMapper
import com.plub.data.mapper.SubHobbyResponseMapper
import com.plub.data.mapper.registerInterestMapper.InterestRegisterResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HobbyRepositoryImpl @Inject constructor(private val hobbyApi: HobbyApi, private val homeApi: HomeApi) : HobbyRepository, BaseRepository() {

    override suspend fun allHobbies(): Flow<UiState<List<HobbyVo>>> {
        return apiLaunch(hobbyApi.allCategories(),AllHobbiesResponseMapper)
    }

    override suspend fun subHobbies(request: Int): Flow<UiState<List<SubHobbyVo>>> {
        return apiLaunch(hobbyApi.subCategories(request), SubHobbyResponseMapper)
    }

    override suspend fun registerInterest(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(homeApi.registerHobby(RegisterHobbiesRequest(request)), InterestRegisterResponseMapper)
    }

    override suspend fun browseInterest(): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(homeApi.browseRegisteredInterest(), InterestRegisterResponseMapper)
    }

    override suspend fun getCategoryList(): Flow<UiState<CategoryListDataResponseVo>> {
        return apiLaunch(hobbyApi.fetchCategoryList(), CategoryListResponseMapper)
    }
}
