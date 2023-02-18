package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.graphics.Bitmap
import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndImageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyRecruitViewModel @Inject constructor(
    private val getRecruitDetailUseCase: GetRecruitDetailUseCase
) : BaseViewModel<ModifyRecruitPageState>(ModifyRecruitPageState()) {

    fun initPageState(bundlePageState: ModifyRecruitPageState) {
        updateUiState { uiState ->
            uiState.copy(
                title = bundlePageState.title,
                name = bundlePageState.name,
                goal = bundlePageState.goal,
                introduce = bundlePageState.introduce,
                plubbingMainImgUrl = bundlePageState.plubbingMainImgUrl,
                tempPlubbingMainBitmap = bundlePageState.tempPlubbingMainBitmap
            )
        }
    }

    fun updateIntroductionTitle(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(title = text.toString())
        }
    }

    fun updateGatheringName(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(name = text.toString())
        }
    }

    fun updateGatheringGoal(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(goal = text.toString())
        }
    }

    fun updateGatheringIntroduce(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(introduce = text.toString())
        }
    }

    fun onClickAddSingleImageButton() {
        emitEventFlow(ModifyRecruitEvent.ShowSelectImageBottomSheetDialog)
    }

}