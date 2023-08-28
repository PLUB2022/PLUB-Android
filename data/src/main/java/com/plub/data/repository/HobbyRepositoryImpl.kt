package com.plub.data.repository

import com.plub.data.api.CategoryApi
import com.plub.data.api.HomeApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.registerHobbies.RegisterHobbiesRequest
import com.plub.data.mapper.AllHobbiesResponseMapper
import com.plub.data.mapper.CategoryDefaultImageResponseMapper
import com.plub.data.mapper.CategoryListResponseMapper
import com.plub.data.mapper.SubHobbyResponseMapper
import com.plub.data.mapper.registerInterestMapper.InterestRegisterResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.CategoryError
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryDefaultImageResponseVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HobbyRepositoryImpl @Inject constructor(private val hobbyApi: CategoryApi, private val homeApi: HomeApi) : HobbyRepository, BaseRepository() {

    override suspend fun allHobbies(): Flow<UiState<List<HobbyVo>>> {
        return apiLaunch(apiCall = { hobbyApi.allCategories() },AllHobbiesResponseMapper){
            CategoryError.make(it)
        }
    }

    override suspend fun subHobbies(request: Int): Flow<UiState<List<SubHobbyVo>>> {
        return apiLaunch(apiCall = { hobbyApi.subCategories(request) }, SubHobbyResponseMapper){
            CategoryError.make(it)
        }
    }

    override suspend fun registerInterest(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(apiCall = { homeApi.registerHobby(RegisterHobbiesRequest(request)) }, InterestRegisterResponseMapper){
            CategoryError.make(it)
        }
    }

    override suspend fun browseInterest(): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(apiCall = { homeApi.browseRegisteredInterest() }, InterestRegisterResponseMapper){
            CategoryError.make(it)
        }
    }

    override suspend fun getCategoryList(): Flow<UiState<CategoryListDataResponseVo>> {
        return apiLaunch(apiCall = { hobbyApi.fetchCategoryList() }, CategoryListResponseMapper){
            CategoryError.make(it)
        }
    }

    override suspend fun getCategoryDefaultImage(categoryIdRequest: Int, subCategoryIdRequest: Int) : Flow<UiState<CategoryDefaultImageResponseVo>> {
        return apiLaunch(apiCall = { hobbyApi.getDefaultCategoryImage(categoryIdRequest, subCategoryIdRequest)}, CategoryDefaultImageResponseMapper){
            CategoryError.make(it)
        }
    }
}
