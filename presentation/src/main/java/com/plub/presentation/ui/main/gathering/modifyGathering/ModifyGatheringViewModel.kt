package com.plub.presentation.ui.main.gathering.modifyGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.modifyGathering.recruit.RecruitPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyGatheringViewModel @Inject constructor(
    private val getRecruitDetailUseCase: GetRecruitDetailUseCase
) : BaseViewModel<ModifyGatheringPageState>(ModifyGatheringPageState()) {

    fun getGatheringInfoDetail(plubbingId: Int) {
        viewModelScope.launch {
            getRecruitDetailUseCase(plubbingId).collect { state ->
                inspectUiState(
                    state,
                    succeedCallback = { handleGetGatheringInfoSuccess(it) },
                    individualErrorCallback = null
                )
            }
        }
    }

    private fun handleGetGatheringInfoSuccess(data: RecruitDetailResponseVo) {
        updateUiState { uiState ->
            uiState.copy(
                recruitPageState = getRecruitPageState(data)
            )
        }
    }

    private fun getRecruitPageState(data: RecruitDetailResponseVo): RecruitPageState {
        return RecruitPageState(
            title = data.recruitTitle,
            name = data.plubbingName,
            goal = data.plubbingGoal,
            introduce = data.recruitIntroduce,
            plubbingMainImgUrl = data.plubbingMainImage,
        )
    }

}