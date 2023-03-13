package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import com.plub.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : UseCase<CreateScheduleRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: CreateScheduleRequestVo): Flow<UiState<Unit>> {
        return scheduleRepository.editSchedule(request)
    }
}