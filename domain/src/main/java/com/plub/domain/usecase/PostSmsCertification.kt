package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.account.SmsCertificationRequestVo
import com.plub.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostSmsCertification @Inject constructor(
    private val accountRepository: AccountRepository,
) : UseCase<SmsCertificationRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: SmsCertificationRequestVo): Flow<UiState<Unit>> {
        return accountRepository.smsCertification(request)
    }
}