package com.plub.presentation.ui.main.profile.recruiting

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.GatheringError
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.recruitDetailVo.host.AccountsVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.domain.model.vo.home.recruitDetailVo.host.HostApplicantsResponseVo
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.domain.usecase.GetRecruitApplicantsUseCase
import com.plub.domain.usecase.PostApprovalApplicantsRecruitUseCase
import com.plub.domain.usecase.PostRefuseApplicantsRecruitUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.main.profile.MyPageApplicantsGatheringState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class RecruitingGatheringViewModel @Inject constructor(
    val getRecruitApplicantsUseCase: GetRecruitApplicantsUseCase,
    val postApprovalApplicantsRecruitUseCase: PostApprovalApplicantsRecruitUseCase,
    val postRefuseApplicantsRecruitUseCase: PostRefuseApplicantsRecruitUseCase,
    val fetchPlubingMainUseCase: FetchPlubingMainUseCase
) : BaseTestViewModel<MyPageApplicantsGatheringState>() {

    var plubbingId: Int by Delegates.notNull()
    private val detailListStateFlow : MutableStateFlow<List<MyPageDetailVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: MyPageApplicantsGatheringState = MyPageApplicantsGatheringState(
        detailList = detailListStateFlow.asStateFlow()
    )

    fun getPageDetail(id: Int) {
        this.plubbingId = id
        viewModelScope.launch {
            fetchPlubingMainUseCase(plubbingId).collect {
                inspectUiState(it, ::onSuccessPlubingMainInfo){ _, individual ->
                    handleGatheringError(individual as GatheringError)
                }
            }

            getRecruitApplicantsUseCase(plubbingId).collect {
                inspectUiState(it, ::handleGetApplicantsSuccess){ _, individual ->
                    handleGatheringError(individual as GatheringError)
                }
            }
        }
    }

    private fun handleGatheringError(gatheringError: GatheringError) {
        when (gatheringError) {
            is GatheringError.AlreadyAccepted -> TODO()
            is GatheringError.AlreadyApplied -> TODO()
            is GatheringError.AlreadyFinish -> TODO()
            is GatheringError.AlreadyRecruitDone -> TODO()
            is GatheringError.AlreadyRejected -> TODO()
            GatheringError.Common -> TODO()
            is GatheringError.FullMemberPlubbing -> TODO()
            is GatheringError.HostCannotApply -> TODO()
            is GatheringError.LimitMaxPlubbing -> TODO()
            is GatheringError.LimitPullUp -> TODO()
            is GatheringError.NotAppliedApplicant -> TODO()
            is GatheringError.NotFoundPlubbing -> TODO()
            is GatheringError.NotFoundQuestion -> TODO()
            is GatheringError.NotFoundRecruit -> TODO()
            is GatheringError.NotFoundSubCategory -> TODO()
            is GatheringError.NotHost -> TODO()
            is GatheringError.NotJoinedPlubbing -> TODO()
            is GatheringError.NotMemberPlubbing -> TODO()
        }
    }

    private fun onSuccessPlubingMainInfo(mainVo: PlubingMainVo) {
        val topView = MyPageDetailTitleVo(
            title = mainVo.name,
            date = mainVo.days,
            placeName = mainVo.placeName,
            time = mainVo.time,
        )
        updateDetailList(getMergedTopList(topView))
    }

    private fun getMergedTopList(view: MyPageDetailTitleVo): List<MyPageDetailVo> {
        return arrayListOf(
            MyPageDetailVo(
                viewType = MyPageDetailViewType.TOP,
                title = view
            ),
            MyPageDetailVo(
                viewType = MyPageDetailViewType.OTHER_APPLICANTS_TEXT,
            )
        )
    }

    private fun handleGetApplicantsSuccess(state: HostApplicantsResponseVo) {
        val originList = uiState.detailList.value
        val mergedList = getMergedList(state.appliedAccounts)
        updateDetailList(originList + mergedList)
    }

    private fun updateDetailList(list: List<MyPageDetailVo>) {
        viewModelScope.launch{
            detailListStateFlow.update { list }
        }
    }

    private fun getMergedList(list: List<AccountsVo>): List<MyPageDetailVo> {
        return if(list.isEmpty()) getEmptyApplicantsList() else getApplicantsList(list)
    }

    private fun getEmptyApplicantsList() : List<MyPageDetailVo>{
        return arrayListOf(MyPageDetailVo(
            viewType = MyPageDetailViewType.EMPTY,
        ))
    }

    private fun getApplicantsList(list : List<AccountsVo>): List<MyPageDetailVo>{
        val detailList = list.map {
            MyPageDetailVo(
                viewType = MyPageDetailViewType.OTHER_APPLICATION,
                application = it
            )
        }
        return detailList
    }

    fun approve(accountId: Int) {
        viewModelScope.launch {
            postApprovalApplicantsRecruitUseCase(
                ReplyApplicantsRecruitRequestVo(plubbingId, accountId)
            ).collect { state ->
                inspectUiState(state, succeedCallback = { handleReplySuccess(accountId) }){ _, individual ->
                    handleGatheringError(individual as GatheringError)
                }
            }
        }
    }

    private fun handleReplySuccess(accountId: Int) {
        val list = uiState.detailList.value.filter {
            it.application.accountId != accountId
        }
        updateDetailList(list)
    }

    fun reject(accountId: Int) {
        viewModelScope.launch {
            postRefuseApplicantsRecruitUseCase(
                ReplyApplicantsRecruitRequestVo(plubbingId, accountId)
            ).collect { state ->
                inspectUiState(state, succeedCallback = { handleReplySuccess(accountId) }){ _, individual ->
                    handleGatheringError(individual as GatheringError)
                }
            }
        }
    }

    fun goToRecruitPage() {
        emitEventFlow(MyPageRecruitingGatheringEvent.GoToRecruit)
    }

    fun showApproveDialog(accountId: Int){
        emitEventFlow(MyPageRecruitingGatheringEvent.ShowApproveDialog(accountId))
    }

    fun showRefuseDialog(accountId: Int){
        emitEventFlow(MyPageRecruitingGatheringEvent.ShowRefuseDialog(accountId))
    }

    fun goToBack(){
        emitEventFlow(MyPageRecruitingGatheringEvent.GoToBack)
    }
}