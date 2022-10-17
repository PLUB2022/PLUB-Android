package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin
import kotlinx.coroutines.flow.Flow

interface IntroRepository {
    fun trySampleLogin(): Flow<UiState<SampleLogin>>

}