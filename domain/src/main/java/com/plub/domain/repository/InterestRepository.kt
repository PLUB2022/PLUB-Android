package com.plub.domain.repository

interface InterestRepository {
    suspend fun registerInterest(request: List<Int>): Unit
}
