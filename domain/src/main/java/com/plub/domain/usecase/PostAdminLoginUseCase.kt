package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostAdminLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
):UseCase<Unit, Flow<UiState<SocialLoginResponseVo>>>() {
    override suspend operator fun invoke(request: Unit): Flow<UiState<SocialLoginResponseVo>> {
        return loginRepository.adminLogin()
    }
}