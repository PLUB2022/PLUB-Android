package com.plub.presentation.ui.main.home.profile.recruiting

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.AnswersVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
import com.plub.domain.model.vo.myPage.MyPageApplicationsVo
import com.plub.domain.usecase.GetRecruitApplicantsUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitingGatheringViewModel @Inject constructor(
    val getRecruitApplicantsUseCase: GetRecruitApplicantsUseCase
) : BaseViewModel<MyPageApplicantsGatheringState>(MyPageApplicantsGatheringState()) {

    fun getPageDetail(plubbingId : Int){
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
        updateUiState {uiState ->
            uiState.copy(
                detailList = originList + mergedList
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
}