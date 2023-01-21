package com.plub.data.repository

import com.plub.data.api.InterestApi
import com.plub.data.dto.InterestRequset
import com.plub.domain.repository.InterestRepository
import javax.inject.Inject

class InterestReposImpl @Inject constructor(private val interestApi: InterestApi) : InterestRepository {
    override suspend fun registerInterest(request: List<Int>) {
        return interestApi.registerHobby(InterestRequset(request))
    }
}