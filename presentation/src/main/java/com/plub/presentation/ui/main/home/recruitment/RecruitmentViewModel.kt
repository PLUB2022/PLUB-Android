package com.plub.presentation.ui.main.home.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.usecase.DeleteMyApplicationUseCase
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentViewModel @Inject constructor(
    val getRecruitDetailUseCase: GetRecruitDetailUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val deleteMyApplicationUseCase: DeleteMyApplicationUseCase
) : BaseViewModel<DetailRecruitPageState>(DetailRecruitPageState()) {

    companion object{
        const val SEPARATOR_OF_DAY = ", "
    }

    private var plubbingId : Int = 0

    fun fetchRecruitmentDetail(Id : Int){
        plubbingId = Id
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

    fun clickBookmark(){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase(plubbingId).collect{
                inspectUiState(it, ::successBookMarkChange)
            }
        }
    }

    private fun successBookMarkChange(data : PlubBookmarkResponseVo){
        updateUiState { ui->
            ui.copy(
                isBookmarked = data.isBookmarked
            )
        }
    }

    fun goToApplyPlubbing(){
        if(uiState.value.isApplied) emitEventFlow(RecruitEvent.CancelApply)
        else emitEventFlow(RecruitEvent.GoToApplyPlubbingFragment)
    }

    fun goToBack(){
        emitEventFlow(RecruitEvent.GoToBack)
    }

    fun goToProfile(accountId : Int, nickname : String){
        emitEventFlow(RecruitEvent.GoToProfileFragment(accountId, nickname))
    }

    fun openBottomSheet(){
        emitEventFlow(RecruitEvent.OpenBottomSheet(uiState.value.joinedAccounts))
    }

    fun onClickReport(){
        emitEventFlow(RecruitEvent.GoToReport)
    }

    fun cancelApply(){
        viewModelScope.launch {
            deleteMyApplicationUseCase(plubbingId).collect{
                inspectUiState(it, { goToBack() })
            }
        }
    }
}