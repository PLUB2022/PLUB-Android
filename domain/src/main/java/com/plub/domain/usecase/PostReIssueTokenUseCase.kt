package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenVo
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.LoginRepository
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostReIssueTokenUseCase @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
):UseCase<String, Flow<PlubJwtTokenVo>>() {
    override operator fun invoke(request: String): Flow<PlubJwtTokenVo> {
        return plubJwtTokenRepository.reIssueToken(request)
    }
}