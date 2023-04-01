package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import com.plub.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUpdateMyInfoUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) : UseCase<UpdateMyInfoRequestVo, Flow<UiState<MyInfoResponseVo>>>() {
    override suspend operator fun invoke(request: UpdateMyInfoRequestVo): Flow<UiState<MyInfoResponseVo>> {
        return accountRepository.updateMyInfo(request)
    }
}