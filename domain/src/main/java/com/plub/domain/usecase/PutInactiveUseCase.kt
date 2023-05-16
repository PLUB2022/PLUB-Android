package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutInactiveUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Boolean, Flow<UiState<Unit>>>() {

    override suspend operator fun invoke(request: Boolean): Flow<UiState<Unit>> {
        return accountRepository.inactive(request)
    }
}