package com.plub.domain.usecase

import com.plub.domain.util.RegexUtil
import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.error.NicknameError
import com.plub.domain.model.vo.signUp.profile.NicknameCheckRequestVo
import com.plub.domain.repository.SignUpRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetNicknameCheckUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) : UseCase<NicknameCheckRequestVo, Flow<UiState<Boolean>>>() {

    companion object {
        private const val NICKNAME_CHECK_DELAY = 500L
    }

    private var job: Deferred<Flow<UiState<Boolean>>>? = null

    override suspend operator fun invoke(request: NicknameCheckRequestVo): Flow<UiState<Boolean>> {
        request.run {
            job?.cancel()

            return when {
                nickname.isEmpty() -> flow { emit(UiState.Error(false, NicknameError.EmptyNickname("비어있는 닉네임"))) }
                RegexUtil.hasSpecialCharacter(nickname) -> flow { emit(UiState.Error(false, NicknameError.HasSpecialCharacter("특수문자 포함된 닉네임"))) }
                RegexUtil.hasBlackCharacter(nickname) -> flow { emit(UiState.Error(false, NicknameError.HasBlankNickname("공백 포함된 닉네임"))) }
                else -> {
                    job = request.scope.async {
                        delay(NICKNAME_CHECK_DELAY)
                        signUpRepository.nicknameCheck(nickname)
                    }
                    job?.await() ?: flow { emit(UiState.Error(false, NicknameError.Common)) }
                }
            }
        }
    }
}