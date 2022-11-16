package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostSocialLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
):UseCase<SocialLoginRequestVo, SocialLoginResponseVo>() {
    override operator fun invoke(request: SocialLoginRequestVo): Flow<UiState<SocialLoginResponseVo>> {
        return loginRepository.socialLogin(request)
    }
}