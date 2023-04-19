package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOtherProfileUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<String, Flow<UiState<MyInfoResponseVo>>>() {
    override suspend operator fun invoke(request: String): Flow<UiState<MyInfoResponseVo>> {
        return accountRepository.fetchOtherInfo(request)
    }
}