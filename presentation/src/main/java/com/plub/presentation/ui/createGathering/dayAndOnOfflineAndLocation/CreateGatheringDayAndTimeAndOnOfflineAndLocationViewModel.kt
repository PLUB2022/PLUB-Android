package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringDayAndTimeAndOnOfflineAndLocationViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState>(
        CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState()
    ) {
    fun onClickTimeTextView() {
        emitEventFlow(CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent.ShowTimePickerDialog)
    }

    fun setGatheringHourAndMinuteAndFormattedText(hour: Int, min: Int) {
        updateUiState { ui ->
            ui.copy(
                gatheringHour = hour,
                gatheringMin = min,
                gatheringFormattedTime = TimeFormatter.getAmPmHourMin(hour, min)
            )
        }
    }

    fun initUiState(savedUiState: PageState) {
        if (uiState.value != CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState())
            return

        if (savedUiState is CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState) {
            updateUiState { uiState ->
                uiState.copy(
                    gatheringDays = savedUiState.gatheringDays,
                    gatheringOnOffline = savedUiState.gatheringOnOffline,
                    gatheringHour = savedUiState.gatheringHour,
                    gatheringMin = savedUiState.gatheringMin,
                    gatheringFormattedTime = savedUiState.gatheringFormattedTime
                )
            }

            updateGatheringLocationData(savedUiState.gatheringLocationData)
        }
    }

    fun updateGatheringLocationData(data: KakaoLocationInfoDocumentVo?) {
        viewModelScope.launch {
            updateUiState { uiState ->
                uiState.copy(
                    gatheringLocationData = data
                )
            }
        }
    }

    fun onClickIconEditTextLocation() {
        emitEventFlow(CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent.ShowBottomSheetSearchLocation)
    }

    /**
     * https://stackoverflow.com/questions/60005152/cannot-use-same-bindingadapter-on-two-different-views
     * Unit으로 할 경우 DataBinding Complier가 올바르지 않은 java code를 생성함.
     * 따라서 Void를 사용
     */
    fun onClickCheckBox(element: DaysType): Void? {
        updateUiState { uiState ->
            uiState.copy(
                gatheringDays = uiState.gatheringDays
                    .addOrRemoveElementAfterReturnNewHashSet(element)
                    .removeElementAfterReturnNewHashSet(DaysType.ALL)
            )
        }
        return null
    }

    /**
     * https://stackoverflow.com/questions/60005152/cannot-use-same-bindingadapter-on-two-different-views
     * Unit으로 할 경우 DataBinding Complier가 올바르지 않은 java code를 생성함.
     * 따라서 Void를 사용
     */
    fun onClickAllCheckBox(): Void? {
        updateUiState { uiState ->
            uiState.copy(
                gatheringDays = if (DaysType.ALL in uiState.gatheringDays) hashSetOf() else hashSetOf(
                    DaysType.ALL
                )
            )
        }
        return null
    }

    fun onClickOnlineButton() {
        updateUiState { uiState ->
            uiState.copy(
                gatheringOnOffline = OnOfflineType.ON.value
            )
        }
    }

    fun onClickOfflineButton() {
        updateUiState { uiState ->
            uiState.copy(
                gatheringOnOffline = OnOfflineType.OFF.value
            )
        }
    }
}