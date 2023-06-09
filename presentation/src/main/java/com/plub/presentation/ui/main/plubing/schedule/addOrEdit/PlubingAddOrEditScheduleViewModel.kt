package com.plub.presentation.ui.main.plubing.schedule.addOrEdit

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.model.vo.schedule.EditScheduleRequestVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.domain.usecase.PostScheduleUseCase
import com.plub.domain.usecase.PutScheduleUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlubingAddOrEditScheduleViewModel @Inject constructor(
    private val postScheduleUseCase: PostScheduleUseCase,
    private val putScheduleUseCase: PutScheduleUseCase
) : BaseViewModel<PlubingAddOrEditSchedulePageState>(PlubingAddOrEditSchedulePageState()) {

    fun updatePlubbingId(plubbingId: Int) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = plubbingId
            )
        }
    }

    fun updatePageState(scheduleVo: ScheduleVo?) {
        scheduleVo?.let {
            updateUiState { uiState ->
                uiState.copy(
                    isEditMode = true,
                    calendarId = it.calendarId,
                    scheduleTitle = it.title,
                    isAllDay = it.isAllDay,
                    startScheduleDate = ScheduleDate(TimeFormatter.getIntYearFromyyyyDashmmDashddFormat(it.startedAt)),
                    startScheduleTime = ScheduleTime(TimeFormatter.getIntHourIntMin(it.startTime)),
                    endScheduleDate = ScheduleDate(TimeFormatter.getIntYearFromyyyyDashmmDashddFormat(it.endedAt)),
                    endScheduleTime = ScheduleTime(TimeFormatter.getIntHourIntMin(it.endTime)),
                    location = KakaoLocationInfoDocumentVo(
                        placeName = it.placeName,
                        addressName = it.address,
                        roadAddressName = it.roadAddress
                    ),
                    alarm = DialogCheckboxItemType.typeOf(it.alarmType),
                    memo = it.memo
                )
            }
        }
    }

    fun updateAlarm(alarm: DialogCheckboxItemType) {
        updateUiState { uiState ->
            uiState.copy(alarm = alarm)
        }
    }
    fun updateScheduleTitle(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(scheduleTitle = text.toString())
        }
    }

    fun updateMemo(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(memo = text.toString())
        }
    }

    fun onClickIsAllDay() {
        updateUiState { uiState ->
            uiState.copy(isAllDay = uiState.isAllDay.not())
        }
    }

    fun onClickStartDateTime() {
        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowStartDatePickerEventOrEdit { year, month, day ->
                updateStartDateAndEmitShowStartTimePickerEvent(year, month, day)
            }
        )
    }

    fun onClickEndDateTime() {
        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowEndDatePickerEventOrEdit { year, month, day ->
                updateEndDateAndEmitShowEndTimePickerEvent(year, month, day)
            }
        )
    }

    private fun updateStartDateAndEmitShowStartTimePickerEvent(year: Int, month: Int, day: Int) {
        val startScheduleDate = ScheduleDate(
            year, month, day
        )

        updateUiState { uiState ->
            uiState.copy(startScheduleDate = startScheduleDate)
        }

        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowStartTimePickerEventOrEdit { hour, minute ->
                updateStartTime(hour, minute)
            }
        )
    }

    private fun updateStartTime(hour: Int, minute: Int) {
        val startScheduleTime = ScheduleTime(
            hour, minute
        )

        if (uiState.value.startTimeInMills > uiState.value.endTimeInMills) {
            updateUiState { uiState ->
                uiState.copy(
                    endScheduleDate = uiState.startScheduleDate,
                    startScheduleTime = startScheduleTime,
                    endScheduleTime = startScheduleTime
                )
            }
        } else {
            updateUiState { uiState ->
                uiState.copy(startScheduleTime = startScheduleTime)
            }
        }
    }

    private fun updateEndDateAndEmitShowEndTimePickerEvent(year: Int, month: Int, day: Int) {
        val endScheduleDate = ScheduleDate(
            year, month, day
        )

        updateUiState { uiState ->
            uiState.copy(endScheduleDate = endScheduleDate)
        }

        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowEndTimePickerEventOrEdit { hour, minute ->
                updateEndTime(hour, minute)
            }
        )
    }


    private fun updateEndTime(hour: Int, minute: Int) {
        val endScheduleTime = ScheduleTime(
            hour, minute
        )

        val updatedUiState = uiState.value.copy(
            startScheduleDate = uiState.value.endScheduleDate,
            startScheduleTime = endScheduleTime,
            endScheduleTime = endScheduleTime
        )

        if (uiState.value.startTimeInMills > updatedUiState.endTimeInMills) {
            updateUiState {
                updatedUiState
            }
        } else {
            updateUiState { uiState ->
                uiState.copy(endScheduleTime = endScheduleTime)
            }
        }
    }

    fun onClickLocationText() {
        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowBottomSheetSearchLocation
        )
    }

    fun onClickAlarmText() {
        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowBottomSheetDialogSelectAlarm
        )
    }

    fun updateLocationData(data: KakaoLocationInfoDocumentVo) {
        updateUiState { uiState ->
            uiState.copy(
                location = data
            )
        }
    }

    fun onClickFinishButton() {
        if(uiState.value.isEditMode) editSchedule() else createSchedule()
    }

    private fun createSchedule() {
        viewModelScope.launch {
            postScheduleUseCase(getCreateScheduleRequestVo()).collect {
                inspectUiState(it, succeedCallback = { emitGotoScheduleEvent() })
            }
        }
    }

    private fun editSchedule() {
        viewModelScope.launch {
            putScheduleUseCase(getEditScheduleRequestVo()).collect {
                inspectUiState(it, succeedCallback = { emitGotoScheduleEvent() })
            }
        }
    }

    private fun emitGotoScheduleEvent() {
        emitEventFlow(
            PlubingAddOrEditScheduleEvent.GoToOrEditSchedule(uiState.value.plubbingId)
        )
    }

    private fun getCreateScheduleRequestVo(): CreateScheduleRequestVo {
        return with(uiState.value) {
            CreateScheduleRequestVo(
                plubbingId = uiState.value.plubbingId,
                title = scheduleTitle,
                memo = memo,
                isAllDay = isAllDay,
                startedAt = TimeFormatter.getyyyydashMMdashdd(startScheduleDate.year, startScheduleDate.month, startScheduleDate.day),
                endedAt = TimeFormatter.getyyyydashMMdashdd(endScheduleDate.year, endScheduleDate.month, endScheduleDate.day),
                startTime = if(isAllDay) null else TimeFormatter.getHHcolonmm(startScheduleTime.hour, startScheduleTime.minute),
                endTime = if(isAllDay) null else TimeFormatter.getHHcolonmm(endScheduleTime.hour, endScheduleTime.minute),
                address = location.addressName,
                roadAddress = location.roadAddressName,
                placeName = location.placeName,
                alarmType = alarm.value
            )
        }
    }

    private fun getEditScheduleRequestVo(): EditScheduleRequestVo {
        return with(uiState.value) {
            EditScheduleRequestVo(
                plubbingId = uiState.value.plubbingId,
                calendarId = uiState.value.calendarId,
                title = scheduleTitle,
                memo = memo,
                isAllDay = isAllDay,
                startedAt = TimeFormatter.getyyyydashMMdashdd(startScheduleDate.year, startScheduleDate.month, startScheduleDate.day),
                endedAt = TimeFormatter.getyyyydashMMdashdd(endScheduleDate.year, endScheduleDate.month, endScheduleDate.day),
                startTime = if(isAllDay) null else TimeFormatter.getHHcolonmm(startScheduleTime.hour, startScheduleTime.minute),
                endTime = if(isAllDay) null else TimeFormatter.getHHcolonmm(endScheduleTime.hour, endScheduleTime.minute),
                address = location.addressName,
                roadAddress = location.roadAddressName,
                placeName = location.placeName,
                alarmType = alarm.value
            )
        }
    }

    fun goToBack(){
        emitEventFlow(PlubingAddOrEditScheduleEvent.GoToBack)
    }
}
