package com.plub.presentation.ui.main.home.recruitment.hostrecruitment

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitApplicantsUseCase
import com.plub.domain.usecase.PutEndRecruitUseCase
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.home.recruitment.DetailRecruitPageState
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostRecruitmentViewModel @Inject constructor(
    val getRecruitDetailUseCase: GetRecruitDetailUseCase,
    val getRecruitApplicantsUseCase: GetRecruitApplicantsUseCase,
    val putEndRecruitUseCase: PutEndRecruitUseCase
) : BaseViewModel<DetailRecruitPageState>(DetailRecruitPageState()) {

    companion object{
        const val SEPARATOR_OF_DAY = ", "
    }
    fun fetchRecruitmentDetail(plubbingId : Int){
        viewModelScope.launch {
            getRecruitDetailUseCase(plubbingId).collect{ state ->
                inspectUiState(state, ::handleSuccessGetRecruitDetail)
            }
        }
    }

    private fun handleSuccessGetRecruitDetail(data : RecruitDetailResponseVo){
        val days = data.plubbingDays.joinToString(SEPARATOR_OF_DAY)
        val time = TimeFormatter.getAmPmHourMin(data.plubbingTime)
        updateUiState { ui->
            ui.copy(
                recruitTitle = data.recruitTitle,
                recruitIntroduce = data.recruitIntroduce,
                categories = data.categories,
                plubbingName = data.plubbingName,
                plubbingGoal = data.plubbingGoal,
                plubbingMainImage = data.plubbingMainImage,
                plubbingDays = days,
                placeName = data.placeName,
                accountNum = (data.remainAccountNum + data.curAccountNum).toString(),
                plubbingTime = time,
                isBookmarked = data.isBookmarked,
                isApplied = data.isApplied,
                joinedAccounts = data.joinedAccounts

            )
        }
    }

    fun seeApplicants(){
        viewModelScope.launch {
            //getRecruitApplicantsUseCase(uiState.value.plubId)
        }
        emitEventFlow(HostDetailPageEvent.GoToSeeApplicants)
    }

    fun endRecruit(){
        viewModelScope.launch {
            //putEndRecruitUseCase(uiState.value.plubId)
        }
        emitEventFlow(HostDetailPageEvent.GoToBack)
    }

    fun backToMain(){
        emitEventFlow(HostDetailPageEvent.GoToBack)
    }

    fun goToEditPage(){
        emitEventFlow(HostDetailPageEvent.GoToEditFragment)
    }

}