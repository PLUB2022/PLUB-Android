package com.plub.domain.usecase

import com.plub.domain.RegexUtil
import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.model.vo.login.SocialLoginRequestVo
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.repository.LoginRepository
import com.plub.domain.repository.SignUpRepository
import com.plub.domain.result.NicknameFailure
import com.plub.domain.result.StateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNicknameCheckUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) : UseCase<String, Flow<UiState<Boolean>>>() {
    override operator fun invoke(request: String): Flow<UiState<Boolean>> {
        return when {
            request.isEmpty() -> flow {
                emit(UiState.Success(false, NicknameFailure.EmptyNickname("비어있는 닉네임")))
            }
            RegexUtil.hasSpecialCharacter(request) -> flow {
                emit(UiState.Success(false, NicknameFailure.HasSpecialCharacter("특수문자 포함된 닉네임")))
            }
            RegexUtil.hasBlackCharacter(request) -> flow {
                emit(UiState.Success(false, NicknameFailure.HasBlankNickname("공백 포함된 닉네임")))
            }
            else -> signUpRepository.nicknameCheck(request)
        }
    }
}