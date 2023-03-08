package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.repository.PlubingBoardRepository
import com.plub.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEntireScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : UseCase<GetEntireScheduleRequestVo, Flow<UiState<GetEntireScheduleResponseVo>>>() {
    override suspend operator fun invoke(request: GetEntireScheduleRequestVo): Flow<UiState<GetEntireScheduleResponseVo>> {
        return scheduleRepository.getEntireSchedule(request)
    }
}