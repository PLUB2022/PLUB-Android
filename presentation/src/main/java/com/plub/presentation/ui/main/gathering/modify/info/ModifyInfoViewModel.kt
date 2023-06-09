package com.plub.presentation.ui.main.gathering.modify.info

import android.app.Activity
import android.net.Uri
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.UiState
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.usecase.PostChangeFileUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutModifyRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.main.gathering.create.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionEvent
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ModifyInfoViewModel @Inject constructor() : BaseViewModel<ModifyInfoPageState>(ModifyInfoPageState()) {



    fun initPageState(bundlePageState: ModifyInfoPageState) {
//        updateUiState { uiState ->
//            uiState.copy(
//                plubbingId = bundlePageState.plubbingId,
//                title = bundlePageState.title,
//                name = bundlePageState.name,
//                goal = bundlePageState.goal,
//                introduce = bundlePageState.introduce,
//                plubbingMainImgUrl = bundlePageState.plubbingMainImgUrl,
//                tempPlubbingMainBitmap = bundlePageState.tempPlubbingMainBitmap
//            )
//        }
    }

//    fun initUiState(savedUiState: PageState) {
//        if (uiState.value != CreateGatheringPeopleNumberPageState())
//            return
//
//        if (savedUiState is CreateGatheringPeopleNumberPageState) {
//            updateUiState { uiState ->
//                uiState.copy(
//                    seekBarProgress = savedUiState.seekBarProgress,
//                    seekBarPositionX = savedUiState.seekBarPositionX
//                )
//            }
//        }
//    }

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

    fun updateGatheringLocationData(data: KakaoLocationInfoDocumentVo?) {
//        viewModelScope.launch {
//            updateUiState { uiState ->
//                uiState.copy(
//                    gatheringLocationData = data
//                )
//            }
//        }
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