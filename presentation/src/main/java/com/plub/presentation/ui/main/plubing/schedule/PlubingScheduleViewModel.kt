package com.plub.presentation.ui.main.plubing.schedule

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.AttendStatus
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.ScheduleCardType
import com.plub.domain.model.vo.schedule.CalendarAttendListVo
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.domain.model.vo.schedule.DeleteScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleRequestVo
import com.plub.domain.model.vo.schedule.GetEntireScheduleResponseVo
import com.plub.domain.model.vo.schedule.PutScheduleAttendRequestVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.domain.usecase.DeleteScheduleUseCase
import com.plub.domain.usecase.GetEntireScheduleUseCase
import com.plub.domain.usecase.PutScheduleAttendUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndImageEvent
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlubingScheduleViewModel @Inject constructor(
    private val getEntireScheduleUseCase: GetEntireScheduleUseCase,
    private val putScheduleAttendUseCase: PutScheduleAttendUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase
) : BaseViewModel<PlubingSchedulePageState>(PlubingSchedulePageState()) {

    fun updatePlubbingId(plubbingId: Int) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = plubbingId
            )
        }
    }
    fun fetchEntireScheduleUseCase() {
        viewModelScope.launch {
            getEntireScheduleUseCase(GetEntireScheduleRequestVo(uiState.value.scheduleCursorId, uiState.value.plubbingId)).collect {
                inspectUiState(it, ::handleSuccessGetEntireSchedule)
            }
        }
    }

    fun onClickAddScheduleButton() {
        emitEventFlow(PlubingScheduleEvent.GoToAddSchedule(uiState.value.plubbingId))
    }

    private fun handleSuccessGetEntireSchedule(response: GetEntireScheduleResponseVo) =
        viewModelScope.launch {
            val processedScheduleList =
                processScheduleVoList(response.calendarList.content, response.calendarList.last)
            val mergedScheduleList = mergeScheduleList(processedScheduleList)

            updateUiState { uiState ->
                uiState.copy(
                    scheduleList = mergedScheduleList,
                    scheduleCursorId = mergedScheduleList.findLast { it.viewType == ScheduleCardType.CONTENT }?.calendarId ?: 1
                )
            }
        }


    private suspend fun processScheduleVoList(items: List<ScheduleVo>, isLast: Boolean): List<ScheduleVo> {
        if (items.isEmpty()) return emptyList()
        val processedScheduleVoList = mutableListOf<ScheduleVo>()

        val lastContent =
            uiState.value.scheduleList.findLast { it.viewType == ScheduleCardType.CONTENT }

        /**
         * 기존 Schedule의 마지막 아이템이 존재하고, 새롭게 추가될 Schedule의 시작 년도보다 느리다면
         * RecyclerView에 새롭게 추가될 Schedule의 시작 년도를 표시해줘야한다.
         */
        lastContent?.let { prevSchedule ->
            compareStartDateAndAddYearType(processedScheduleVoList, prevSchedule, items[0])
        } ?: addYearToScheduleVoList(processedScheduleVoList, items[0].startedAt)

        /**
         * 새롭게 추가될 Schedule 중에서 년도가 바뀌는 부분이 있다면
         * RecyclerView에 년도가 바뀜을 나타내는 Ui를 추가해야한다.
         */
        items.asFlow().collectIndexed { index, schedule ->
            if(index > 0) compareStartDateAndAddYearType(processedScheduleVoList, items[index - 1], schedule)
            processedScheduleVoList.add(schedule)
        }

        if (!isLast) processedScheduleVoList.add(ScheduleVo(viewType = ScheduleCardType.LOADING))

        return processedScheduleVoList
    }

    private fun compareStartDateAndAddYearType(items: MutableList<ScheduleVo>, firstSchedule: ScheduleVo, secondSchedule: ScheduleVo) {
        val firstScheduleYear =
            TimeFormatter.getIntYearFromyyyyDashmmDashddFormat(firstSchedule.startedAt)
        val secondScheduleYear =
            TimeFormatter.getIntYearFromyyyyDashmmDashddFormat(secondSchedule.startedAt)

        if (secondScheduleYear != firstScheduleYear) addYearToScheduleVoList(items, secondSchedule.startedAt)
    }
    private fun addYearToScheduleVoList(items: MutableList<ScheduleVo>, startedAt: String) {
        items.add(
            ScheduleVo(
                viewType = ScheduleCardType.YEAR,
                startedAt = startedAt
            )
        )
    }

    private suspend fun mergeScheduleList(items: List<ScheduleVo>): List<ScheduleVo> {
        val mergedList = mutableListOf<ScheduleVo>()

        uiState.value.scheduleList.asFlow().filterNot {
            it.viewType == ScheduleCardType.LOADING
        }.collect {
            mergedList.add(it)
        }

        mergedList.addAll(items)

        return mergedList
    }

    fun emitShowBottomSheetEvent(scheduleVo: ScheduleVo) {
        emitEventFlow(
            PlubingScheduleEvent.ShowBottomSheetScheduleDetail(scheduleVo)
        )
    }

    fun emitShowBottomSheetDialogEditOrDeleteScheduleEvent(scheduleVo: ScheduleVo) {
        emitEventFlow(
            PlubingScheduleEvent.ShowBottomSheetDialogEditOrDeleteSchedule(scheduleVo)
        )
    }

    fun putScheduleAttendYes(calendarId: Int) {
        viewModelScope.launch {
            putScheduleAttendUseCase(
                PutScheduleAttendRequestVo(
                    uiState.value.plubbingId,
                    calendarId,
                    AttendStatus.YES.value
                )
            ).collect { state ->
                inspectUiState(state, succeedCallback = { plusAttendPeople(calendarId, it) })
            }
        }
    }

    private fun plusAttendPeople(calendarId: Int,data: CalendarAttendVo) {

        val scheduleVo = findScheduleVo(calendarId)
        if(scheduleVo.calendarAttendList.calendarAttendList.contains(data)) return

        val newCalendarAttendList = scheduleVo.calendarAttendList.calendarAttendList.plus(data)

        updateAttendPeople(scheduleVo, newCalendarAttendList)
    }

    fun putScheduleAttendNo(calendarId: Int) {
        viewModelScope.launch {
            putScheduleAttendUseCase(
                PutScheduleAttendRequestVo(
                    uiState.value.plubbingId,
                    calendarId,
                    AttendStatus.NO.value
                )
            ).collect { state ->
                inspectUiState(state, succeedCallback = { removeAttendPeople(calendarId, it) })
            }
        }
    }

    private fun findScheduleVo(calendarId: Int): ScheduleVo {
        return uiState.value.scheduleList.find { schedule ->
            schedule.calendarId == calendarId
        } ?: ScheduleVo()
    }

    private fun removeAttendPeople(calendarId: Int,data: CalendarAttendVo) {
        val scheduleVo = findScheduleVo(calendarId)
        val newCalendarAttendList = scheduleVo.calendarAttendList.calendarAttendList.minus(data.copy(
            AttendStatus = AttendStatus.YES.value
        ))
        updateAttendPeople(scheduleVo, newCalendarAttendList)
    }

    private fun updateAttendPeople(scheduleVo: ScheduleVo, calendarAttendList: List<CalendarAttendVo>) {
        updateUiState { uiState ->
            val newScheduleVo = scheduleVo.copy(
                calendarAttendList = CalendarAttendListVo(calendarAttendList)
            )

            val copiedScheduleList = mutableListOf<ScheduleVo>()
            uiState.scheduleList.forEach {
                val copiedSchedule = if(it.calendarId == newScheduleVo.calendarId) newScheduleVo else it
                copiedScheduleList.add(copiedSchedule)
            }

            uiState.copy(
                scheduleList = copiedScheduleList
            )
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType, scheduleVo: ScheduleVo) {
        when (type) {
            DialogMenuItemType.SCHEDULE_DELETE -> {
                deleteSchedule(scheduleVo)
            }

            DialogMenuItemType.SCHEDULE_EDIT -> {

            }

            else -> { }
        }
    }

    private fun deleteSchedule(scheduleVo: ScheduleVo) {
        viewModelScope.launch {
            deleteScheduleUseCase(DeleteScheduleRequestVo(
                plubbingId = uiState.value.plubbingId,
                calendarId = scheduleVo.calendarId
            )).collect {
                inspectUiState(it, succeedCallback = { handleDeleteScheduleSuccess(scheduleVo) })
            }
        }
    }

    private fun handleDeleteScheduleSuccess(scheduleVo: ScheduleVo) {
        updateUiState { uiState ->
            uiState.copy(
                scheduleList = uiState.scheduleList.minus(scheduleVo)
            )
        }
    }
}
