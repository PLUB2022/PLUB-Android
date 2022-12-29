package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.state.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringDayAndOnOfflineAndLocationViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringDayAndOnOfflineAndLocationPageState>(
        CreateGatheringDayAndOnOfflineAndLocationPageState()
    )
{
    /**
     * https://stackoverflow.com/questions/60005152/cannot-use-same-bindingadapter-on-two-different-views
     * Unit으로 할 경우 DataBinding Complier가 올바르지 않은 java code를 생성함.
     * 따라서 Void를 사용
     */
        fun onClickCheckBox(element: String): Void? {
            updateUiState { uiState ->
                uiState.copy(
                    gatheringDays = uiState.gatheringDays
                        .addOrRemoveElementAfterReturnNewHashSet(element)
                        .removeElementAfterReturnNewHashSet(DaysType.ALL.value)
                )
            }
            PlubLogger.logD("gatheringDays = ${uiState.value.gatheringDays}")
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
                gatheringDays = hashSetOf(DaysType.ALL.value)
            )
        }
        PlubLogger.logD("gatheringDays = ${uiState.value.gatheringDays}")
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