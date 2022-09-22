package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin
import com.plub.domain.repository.IntroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrySampleLoginUseCase @Inject constructor(
    private val introRepository: IntroRepository
) {
    operator fun invoke(): Flow<UiState<SampleLogin>> {
      return introRepository.trySampleLogin()
   }
}