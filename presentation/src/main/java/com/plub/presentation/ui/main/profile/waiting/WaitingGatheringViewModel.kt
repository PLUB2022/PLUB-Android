package com.plub.presentation.ui.main.profile.waiting

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.home.recruitDetailVo.host.AccountsVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.domain.model.vo.home.recruitDetailVo.host.AnswersVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.usecase.DeleteMyApplicationUseCase
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.domain.usecase.GetMyApplicationUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.profile.MyPageApplicantsGatheringState
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingGatheringViewModel @Inject constructor(
    private val getMyApplicationUseCase: GetMyApplicationUseCase,
    private val deleteMyApplicationUseCase: DeleteMyApplicationUseCase
) :
    BaseViewModel<MyPageApplicantsGatheringState>(
        MyPageApplicantsGatheringState()
    ) {

    fun getPageDetail(plubbingId : Int) {
        viewModelScope.launch {
            getMyApplicationUseCase(plubbingId).collect{
                inspectUiState(it, ::onSuccessPlubingMainInfo)
            }
        }
    }

    private fun onSuccessPlubingMainInfo(vo: MyPageMyApplicationVo) {
        updateDetailList(getTopList(vo.titleVo) + getMyApplicationList(vo.answerList, vo.recruitDate))
    }

    private fun getTopList(titleView : MyPageDetailTitleVo) : List<MyPageDetailVo>{
        return arrayListOf(
            MyPageDetailVo(
                viewType = MyPageDetailViewType.TOP,
                title = titleView
            ),
            MyPageDetailVo(
                viewType = MyPageDetailViewType.MY_APPLICANTS_TEXT,
            )
        )
    }

    private fun getMyApplicationList(myAnswerList : List<AnswersVo>, date : String) : List<MyPageDetailVo>{
        return arrayListOf(
            MyPageDetailVo(
                viewType = MyPageDetailViewType.MY_APPLICATION,
                application = AccountsVo(
                    profileImage = PlubUser.info.profileImage,
                    accountName = PlubUser.info.nickname,
                    createdAt = date,
                    answers = myAnswerList
                )
            )
        )
    }

    fun goToBack(){
        emitEventFlow(WaitingGatheringEvent.GoToBack)
    }

    fun deleteMyApplication(plubbingId: Int){
        viewModelScope.launch{
            deleteMyApplicationUseCase.invoke(plubbingId).collect{
                inspectUiState(it, { handleSuccessDelete() })
            }
        }
    }

    private fun handleSuccessDelete(){
        val originList = uiState.value.detailList
        val mutableOriginList = originList.toMutableList()

        originList.forEach {
            if(it.viewType == MyPageDetailViewType.MY_APPLICATION) mutableOriginList.remove(it)
        }

        updateDetailList(mutableOriginList)
    }

    private fun updateDetailList(list : List<MyPageDetailVo>){
        updateUiState { uiState ->
            uiState.copy(
                detailList = list
            )
        }
    }

    fun goToModifyApplication(){
        emitEventFlow(WaitingGatheringEvent.GoToModifyApplication)
    }

    fun showCancelDialog(){
        emitEventFlow(WaitingGatheringEvent.ShowCancelDialog)
    }
}