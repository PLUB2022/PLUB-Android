package com.plub.presentation.ui.main.profile.waiting

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.AnswersVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.domain.usecase.GetMyApplicationUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.profile.recruiting.MyPageApplicantsGatheringState
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingGatheringViewModel @Inject constructor(
    val fetchPlubingMainUseCase: FetchPlubingMainUseCase,
    val getMyApplicationUseCase: GetMyApplicationUseCase
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
        updateUiState { uiState ->
            uiState.copy(
                detailList = getTopList(vo.titleVo) + getMyApplicationList(vo.answerList, vo.recruitDate)
            )
        }
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
}