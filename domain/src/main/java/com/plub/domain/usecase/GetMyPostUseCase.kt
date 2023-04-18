package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyPostUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) : UseCase<MyPageActiveRequestVo, Flow<UiState<PlubingBoardListVo>>>(){
    override suspend fun invoke(request : MyPageActiveRequestVo): Flow<UiState<PlubingBoardListVo>> {
        return myPageRepository.getMyPost(request)
    }
}