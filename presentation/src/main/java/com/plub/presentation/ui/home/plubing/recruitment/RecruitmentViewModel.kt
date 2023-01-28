package com.plub.presentation.ui.home.plubing.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.RecruitEvent
import com.plub.presentation.state.DetailRecruitPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentViewModel @Inject constructor(
    val recruitDetailUseCase: GetRecruitDetailUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
) : BaseViewModel<DetailRecruitPageState>(DetailRecruitPageState()) {

    fun fetchRecruitmentDetail(plubbingId : Int){
        viewModelScope.launch {
            recruitDetailUseCase(RecruitDetailRequestVo(plubbingId)).collect{ state ->
                inspectUiState(state, ::handleSuccessGetRecruitDetail)
            }
        }
    }

    private fun handleSuccessGetRecruitDetail(data : RecruitDetailResponseVo){
        updateUiState { ui->
            ui.copy(
                recruitTitle = data.recruitTitle,
                recruitIntroduce = data.recruitIntroduce,
                categories = data.categories,
                plubbingName = data.plubbingName,
                plubbingGoal = data.plubbingGoal,
                //plubbingMainImage = data.plubbingMainImage
                //plubbingDays = data.plubbingDays,
                placeName = data.placeName,
                accountNum = data.remainAccountNum + data.curAccountNum,
                //plubbingTime = data.plubbingTime
                isBookmarked = data.isBookmarked,
                isApplied = data.isApplied,
                joinedAccounts = data.joinedAccounts

            )
        }
    }

    fun clickBookmark(){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase(uiState.value.plubId).collect{
                inspectUiState(it, ::bookMarkChange)
            }
        }
    }

    private fun bookMarkChange(data : PlubBookmarkResponseVo){
        updateUiState { ui->
            ui.copy(
                isBookmarked = data.isBookmarked
            )
        }
    }

    fun goToApplyPlubbingFragment(){
        emitEventFlow(RecruitEvent.GoToApplyPlubbingFragment)
    }


    fun updatePlubState(id : Int){
        updateUiState {ui->
            ui.copy(
                plubId = id
            )
        }
    }

}