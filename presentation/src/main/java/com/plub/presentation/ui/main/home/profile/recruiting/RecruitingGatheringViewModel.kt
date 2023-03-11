package com.plub.presentation.ui.main.home.profile.recruiting

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
import com.plub.domain.usecase.GetRecruitApplicantsUseCase
import com.plub.domain.usecase.PostApprovalApplicantsRecruitUseCase
import com.plub.domain.usecase.PostRefuseApplicantsRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitingGatheringViewModel @Inject constructor(
    val getRecruitApplicantsUseCase: GetRecruitApplicantsUseCase,
    val postApprovalApplicantsRecruitUseCase: PostApprovalApplicantsRecruitUseCase,
    val postRefuseApplicantsRecruitUseCase: PostRefuseApplicantsRecruitUseCase
) : BaseViewModel<MyPageApplicantsGatheringState>(MyPageApplicantsGatheringState()) {

    var plubbingId : Int = 0
    fun getPageDetail(id : Int){
        this.plubbingId = id
        viewModelScope.launch {
            getRecruitApplicantsUseCase(plubbingId).collect{
                inspectUiState(it, ::handleGetApplicantsSuccess)
            }
        }
    }

    private fun handleGetApplicantsSuccess(state : HostApplicantsResponseVo){
        if(uiState.value.detailList.isEmpty()){
            updateFirstPage()
        }
        val originList = uiState.value.detailList
        val mergedList = getMergedList(state)
        updateDetailList(originList + mergedList)
    }

    private fun updateDetailList(list : List<MyPageDetailVo>){
        updateUiState {uiState ->
            uiState.copy(
                detailList = list
            )
        }
    }

    private fun updateFirstPage() {
        //TODO REMOVE 임시
        val list = arrayListOf(
            MyPageDetailVo(
                viewType = MyPageDetailViewType.TOP,
                title = MyPageDetailTitleVo(
                    title = "요란한 한줄",
                    date = arrayListOf("월", "화", "수", "목", "금", "토"),
                    time = "17:30",
                    position = "경기도 의정부시 동일로 474번길"
                )
            ),
            MyPageDetailVo(
                viewType = MyPageDetailViewType.OTHER_APPLICANTS_TEXT,
            ))
        updateUiState { uiState ->
            uiState.copy(
                detailList = list
            )
        }
    }

    private fun getMergedList(state : HostApplicantsResponseVo) : List<MyPageDetailVo>{
        val list = mutableListOf<MyPageDetailVo>()
        state.appliedAccounts.forEach {
            list.add(
                MyPageDetailVo(
                    viewType = MyPageDetailViewType.OTHER_APPLICATION,
                    application = it
                )
            )
        }

        return list
    }

    fun approve(accountId : Int){
        viewModelScope.launch {
            postApprovalApplicantsRecruitUseCase(
                ReplyApplicantsRecruitRequestVo(plubbingId, accountId)).collect{ state ->
                inspectUiState(state, succeedCallback = { data -> handleReplySuccess(data, accountId) })
            }
        }
    }

    private fun handleReplySuccess(replyApplicantsRecruitResponseVo: ReplyApplicantsRecruitResponseVo, accountId: Int) {
        val originList = uiState.value.detailList
        val mutableOriginList = originList.toMutableList()
        mutableOriginList.forEach {
            if(it.application.accountId ==  accountId){
                mutableOriginList.remove(it)
            }
        }
        updateDetailList(mutableOriginList)
    }

    fun reject(accountId: Int){
        viewModelScope.launch {
            postRefuseApplicantsRecruitUseCase(
                ReplyApplicantsRecruitRequestVo(plubbingId, accountId)).collect{ state ->
                inspectUiState(state, succeedCallback = { data -> handleReplySuccess(data, accountId) })
            }
        }
    }
}