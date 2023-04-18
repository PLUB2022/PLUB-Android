package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyToDoWithTitleUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) : UseCase<MyPageActiveRequestVo, Flow<UiState<MyPageToDoWithTitleVo>>>(){
    override suspend fun invoke(request : MyPageActiveRequestVo): Flow<UiState<MyPageToDoWithTitleVo>> {
        return myPageRepository.getMyToDo(request)
    }
}