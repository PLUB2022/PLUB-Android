package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.DeleteScheduleRequestVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import com.plub.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : UseCase<DeleteScheduleRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: DeleteScheduleRequestVo): Flow<UiState<Unit>> {
        return scheduleRepository.deleteSchedule(request)
    }
}