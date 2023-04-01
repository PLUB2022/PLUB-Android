package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyGatheringUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) : UseCase<MyPageGatheringStateType, Flow<UiState<MyPageGatheringVo>>>(){
    override suspend fun invoke(request : MyPageGatheringStateType): Flow<UiState<MyPageGatheringVo>> {
        return myPageRepository.getMyGathering(request)
    }
}