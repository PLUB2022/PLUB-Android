package com.plub.domain.usecase

import com.plub.domain.RegexUtil
import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.error.NicknameError
import com.plub.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNicknameCheckUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) : UseCase<String, Flow<UiState<Boolean>>>() {
    override suspend operator fun invoke(request: String): Flow<UiState<Boolean>> = flow {
        when {
            request.isEmpty() -> emit(UiState.Error(false, NicknameError.EmptyNickname("비어있는 닉네임")))
            RegexUtil.hasSpecialCharacter(request) -> emit(UiState.Error(false, NicknameError.HasSpecialCharacter("특수문자 포함된 닉네임")))
            RegexUtil.hasBlackCharacter(request) -> emit(UiState.Error(false, NicknameError.HasBlankNickname("공백 포함된 닉네임")))
            else -> signUpRepository.nicknameCheck(request).collect { emit(it) }
        }
    }
}