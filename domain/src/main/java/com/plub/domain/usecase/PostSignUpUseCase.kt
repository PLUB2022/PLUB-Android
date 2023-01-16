package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import com.plub.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostSignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
) : UseCase<SignUpRequestVo, Flow<UiState<PlubJwtResponseVo>>>() {
    override suspend operator fun invoke(request: SignUpRequestVo): Flow<UiState<PlubJwtResponseVo>> {
        return signUpRepository.signUp(request)
    }
}