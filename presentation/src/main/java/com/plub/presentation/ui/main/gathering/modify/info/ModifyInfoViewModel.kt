package com.plub.presentation.ui.main.gathering.modify.info

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyInfoViewModel @Inject constructor() : BaseViewModel<ModifyInfoPageState>(ModifyInfoPageState()) {

    fun initPageState(pageState: ModifyInfoPageState, seekBarPositionX: Float) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = pageState.plubbingId,
                gatheringDays = pageState.gatheringDays,
                gatheringOnOffline = pageState.gatheringOnOffline,
                address = pageState.address,
                roadAdress = pageState.roadAdress,
                placeName = pageState.placeName,
                gatheringHour = pageState.gatheringHour,
                gatheringMin = pageState.gatheringMin,
                gatheringFormattedTime = pageState.gatheringFormattedTime,
                seekBarProgress = pageState.seekBarProgress,
                seekBarPositionX = seekBarPositionX
            )
        }
    }

    val updateSeekbarProgressAndPositionX: (progress: Int, position: Float) -> Unit =
        { progress, position ->
            updateSeekbarProgress(progress)
            updateSeekbarPositionX(position)
        }

    private fun updateSeekbarProgress(progress: Int) {
        updateUiState { uiState ->
            uiState.copy(
                seekBarProgress = progress
            )
        }
    }

    private fun updateSeekbarPositionX(position: Float) {
        updateUiState { uiState ->
            uiState.copy(
                seekBarPositionX = position
            )
        }
    }

    fun onClickTimeTextView() {
        emitEventFlow(ModifyInfoEvent.ShowTimePickerDialog)
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

    fun updateGatheringLocationData(data: KakaoLocationInfoDocumentVo?) {
        viewModelScope.launch {
            updateUiState { uiState ->
                checkNotNull(data)
                uiState.copy(
                    address = data.addressName,
                    roadAdress = data.roadAddressName,
                    placeName = data.placeName
                )
            }
        }
    }

    fun onClickIconEditTextLocation() {
        emitEventFlow(ModifyInfoEvent.ShowBottomSheetSearchLocation)
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