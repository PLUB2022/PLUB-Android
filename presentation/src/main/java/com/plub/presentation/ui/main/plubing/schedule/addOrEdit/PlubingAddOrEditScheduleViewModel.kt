package com.plub.presentation.ui.main.plubing.schedule.addOrEdit

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.schedule.CreateScheduleRequestVo
import com.plub.domain.usecase.PostScheduleUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlubingAddOrEditScheduleViewModel @Inject constructor(
    private val postScheduleUseCase: PostScheduleUseCase
) : BaseViewModel<PlubingAddOrEditSchedulePageState>(PlubingAddOrEditSchedulePageState()) {

    fun updatePlubbingId(plubbingId: Int) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = plubbingId
            )
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
        val startDate = Date(
            year, month, day
        )

        updateUiState { uiState ->
            uiState.copy(startDate = startDate)
        }

        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowStartTimePickerEventOrEdit { hour, minute ->
                updateStartTime(hour, minute)
            }
        )
    }

    private fun updateStartTime(hour: Int, minute: Int) {
        val startTime = Time(
            hour, minute
        )

        if (uiState.value.startTimeInMills > uiState.value.endTimeInMills) {
            updateUiState { uiState ->
                uiState.copy(
                    endDate = uiState.startDate,
                    startTime = startTime,
                    endTime = startTime
                )
            }
        } else {
            updateUiState { uiState ->
                uiState.copy(startTime = startTime)
            }
        }
    }

    private fun updateEndDateAndEmitShowEndTimePickerEvent(year: Int, month: Int, day: Int) {
        val endDate = Date(
            year, month, day
        )

        updateUiState { uiState ->
            uiState.copy(endDate = endDate)
        }

        emitEventFlow(
            PlubingAddOrEditScheduleEvent.ShowEndTimePickerEventOrEdit { hour, minute ->
                updateEndTime(hour, minute)
            }
        )
    }


    private fun updateEndTime(hour: Int, minute: Int) {
        val endTime = Time(
            hour, minute
        )

        if (uiState.value.startTimeInMills > uiState.value.endTimeInMills) {
            updateUiState { uiState ->
                uiState.copy(
                    startDate = uiState.endDate,
                    startTime = endTime,
                    endTime = endTime
                )
            }
        } else {
            updateUiState { uiState ->
                uiState.copy(endTime = endTime)
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

    fun createSchedule() {
        viewModelScope.launch {
            postScheduleUseCase(getCreateScheduleRequestVo()).collect {
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
                startedAt = TimeFormatter.getyyyydashMMdashdd(startDate.year, startDate.month, startDate.day),
                endedAt = TimeFormatter.getyyyydashMMdashdd(endDate.year, endDate.month, endDate.day),
                startTime = if(isAllDay) null else TimeFormatter.getHHcolonmm(startTime.hour, startTime.minute),
                endTime = if(isAllDay) null else TimeFormatter.getHHcolonmm(endTime.hour, endTime.minute),
                address = location.addressName,
                roadAddress = location.roadAddressName,
                placeName = location.placeName,
                alarmType = alarm.value
            )
        }
    }
}
