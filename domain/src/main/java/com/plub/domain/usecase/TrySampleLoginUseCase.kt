package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.repository.IntroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrySampleLoginUseCase @Inject constructor(
    private val introRepository: IntroRepository
):UseCase<Unit, SampleLogin>() {
    override fun invoke(request: Unit): Flow<UiState<SampleLogin>> {
        return introRepository.trySampleLogin()
    }
}