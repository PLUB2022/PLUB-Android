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
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlubingScheduleViewModel @Inject constructor(
    private val getEntireScheduleUseCase: GetEntireScheduleUseCase,
    private val putScheduleAttendUseCase: PutScheduleAttendUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase
) : BaseViewModel<PlubingSchedulePageState>(PlubingSchedulePageState()) {

    companion object {
        private const val FIRST_IDX = 0
    }

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
        emitEventFlow(PlubingScheduleEvent.GoToAddSchedule(null))
    }

    private fun handleSuccessGetEntireSchedule(response: GetEntireScheduleResponseVo) =
        viewModelScope.launch {
            val processedScheduleList =
                getProcessedScheduleVoList(response.calendarList.content, response.calendarList.last)
            val mergedScheduleList = mergeScheduleList(processedScheduleList)

            updateUiState { uiState ->
                uiState.copy(
                    scheduleList = mergedScheduleList,
                    scheduleCursorId = mergedScheduleList.findLast { it.viewType == ScheduleCardType.CONTENT }?.calendarId ?: 1
                )
            }
        }


    private fun getProcessedScheduleVoList(items: List<ScheduleVo>, isLast: Boolean): List<ScheduleVo> {
        if (items.isEmpty()) return emptyList()

        val lastContent =
            uiState.value.scheduleList.findLast { it.viewType == ScheduleCardType.CONTENT }

        val lastYear = lastContent?.startedYear ?: -1

        return processScheduleVoList(items, lastYear, isLast)
    }

    private fun processScheduleVoList(items: List<ScheduleVo>, lastYear: Int, isLast: Boolean): List<ScheduleVo> {
        return items.groupBy { it.startedYear }
            .mapValues {
                val isExistDate = lastYear == it.key
                val yearItem = ScheduleVo(
                    viewType = ScheduleCardType.YEAR,
                    startedYear = it.key
                )
                it.value.toMutableList().apply {
                    if (!isExistDate) add(FIRST_IDX, yearItem)
                }
            }.flatMap { it.value }.toMutableList().apply {
                if (!isLast) add(ScheduleVo(viewType = ScheduleCardType.LOADING))
            }
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
        if(!scheduleVo.isEditable) return
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
                onClickEditScheduleButton(scheduleVo)
            }

            else -> { }
        }
    }

    private fun onClickEditScheduleButton(scheduleVo: ScheduleVo) {
        emitEventFlow(PlubingScheduleEvent.GoToAddSchedule(scheduleVo))
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

    fun goToBack(){
        emitEventFlow(PlubingScheduleEvent.GoToBack)
    }
}
