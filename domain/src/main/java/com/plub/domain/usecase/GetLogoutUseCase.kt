package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<Unit, Flow<UiState<Unit>>>() {

    override suspend fun invoke(request: Unit): Flow<UiState<Unit>> {
        return loginRepository.logout()
    }
}