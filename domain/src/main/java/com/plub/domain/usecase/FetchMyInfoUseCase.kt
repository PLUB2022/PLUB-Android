package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.AccountRepository
import com.plub.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchMyInfoUseCase @Inject constructor(
    private val accountRepository: AccountRepository
):UseCase<Unit, Flow<UiState<MyInfoResponseVo>>>() {
    override suspend fun invoke(request: Unit): Flow<UiState<MyInfoResponseVo>> {
        return accountRepository.fetchMyInfo()
    }
}