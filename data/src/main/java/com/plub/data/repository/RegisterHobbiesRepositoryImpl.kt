package com.plub.data.repository

import com.plub.data.api.InterestHobbiesApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.registerhobbies.RegisterHobbiesRequest
import com.plub.data.mapper.registerinterestmapper.InterestRegisterResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.repository.RegisterHobbiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterHobbiesRepositoryImpl @Inject constructor(private val interestApi: InterestHobbiesApi) : RegisterHobbiesRepository, BaseRepository() {
    override suspend fun registerInterest(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(interestApi.registerHobby(RegisterHobbiesRequest(request)), InterestRegisterResponseMapper)
    }

    override suspend fun browseInterest(): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(interestApi.browseRegisteredInterest(), InterestRegisterResponseMapper)
    }
}