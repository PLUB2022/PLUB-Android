package com.plub.data.repository

import com.plub.data.api.InterestApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.registerinterest.InterestRequest
import com.plub.data.mapper.registerinterestmapper.InterestRegisterResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.repository.RegistInterestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistInterestRepositoryImpl @Inject constructor(private val interestApi: InterestApi) : RegistInterestRepository, BaseRepository() {
    override suspend fun registerInterest(request: List<Int>): Flow<UiState<RegisterInterestResponseVo>> {
        return apiLaunch(interestApi.registerHobby(InterestRequest(request)), InterestRegisterResponseMapper)
    }
}