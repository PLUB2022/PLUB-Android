package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyApplicationUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) : UseCase<Int, Flow<UiState<MyPageMyApplicationVo>>>(){
    override suspend fun invoke(request : Int): Flow<UiState<MyPageMyApplicationVo>> {
        return myPageRepository.getMyApplicationWithPlubInfo(request)
    }
}