package com.plub.presentation.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.usecase.GetMyGatheringUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val getMyGatheringUseCase: GetMyGatheringUseCase,
) : BaseTestViewModel<MyPageState>() {

    companion object {
        const val MAX_LENGTH = 15
    }
    private val myPageGatheringListStateFlow : MutableStateFlow<List<MyPageGatheringVo>> = MutableStateFlow(
        emptyList()
    )
    private val isReadMoreStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val myNameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val myIntroStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val profileImageStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: MyPageState = MyPageState(
        myPageGatheringList = myPageGatheringListStateFlow.asStateFlow(),
        isReadMore = isReadMoreStateFlow.asStateFlow(),
        myName = myNameStateFlow.asStateFlow(),
        myIntro = myIntroStateFlow.asStateFlow(),
        profileImage = profileImageStateFlow.asStateFlow(),
        isEmpty = isEmptyStateFlow.asStateFlow()
    )

    private var isExpandText: Boolean = false
    private var recruitingList : List<MyPageGatheringVo> = emptyList()
    private var waitingList : List<MyPageGatheringVo> = emptyList()
    private var activeList : List<MyPageGatheringVo> = emptyList()
    private var endList : List<MyPageGatheringVo> = emptyList()


    fun setMyInfo() {
        viewModelScope.launch {
            myNameStateFlow.update { PlubUser.info.nickname }
            myIntroStateFlow.update { PlubUser.info.introduce }
            profileImageStateFlow.update { PlubUser.info.profileImage }
            isReadMoreStateFlow.update { PlubUser.info.introduce.length > MAX_LENGTH }
        }
    }

    fun refresh(){
        updateMyGathering(emptyList())
    }

    fun getMyPageData() {
        viewModelScope.launch {
            val jobRecruit : Job = launch {
                getMyGatheringUseCase(MyPageGatheringStateType.RECRUITING).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }
            }

            val jobWait : Job = launch {
                getMyGatheringUseCase(MyPageGatheringStateType.WAITING).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }
            }

            val jobActive : Job = launch {
                getMyGatheringUseCase(MyPageGatheringStateType.ACTIVE).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }
            }

            val jobEnd : Job = launch {
                getMyGatheringUseCase(MyPageGatheringStateType.END).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }
            }

            joinAll(jobRecruit, jobWait, jobActive, jobEnd)
            updateMyGathering(recruitingList + waitingList + activeList + endList)
        }
    }

    private fun handleGetMyGatheringSuccess(state: MyPageGatheringVo) {
        if (state.gatheringList.isNotEmpty()) {
            when(state.gatheringType){
                MyPageGatheringStateType.ACTIVE -> {
                    activeList = listOf(state)
                }
                MyPageGatheringStateType.END -> {
                    endList = listOf(state)
                }
                MyPageGatheringStateType.WAITING -> {
                    waitingList = listOf(state)
                }
                MyPageGatheringStateType.RECRUITING -> {
                    recruitingList = listOf(state)
                }
            }
        }
    }

    fun goToDetail(
        gatheringType: MyPageGatheringStateType,
        plubbingId: Int,
        gatheringMyType: MyPageGatheringMyType
    ) {
        when (gatheringType) {
            MyPageGatheringStateType.RECRUITING -> {
                emitEventFlow(MyPageEvent.GoToOtherApplication(plubbingId))
            }
            MyPageGatheringStateType.WAITING -> {
                emitEventFlow(MyPageEvent.GoToMyApplication(plubbingId))
            }
            MyPageGatheringStateType.ACTIVE -> {
                emitEventFlow(MyPageEvent.GoToActiveGathering(plubbingId, gatheringMyType))
            }
            MyPageGatheringStateType.END -> {}
        }
    }

    fun onClickExpand(gatheringType: MyPageGatheringStateType) {
        val gatheringList = uiState.myPageGatheringList.value.map {
            val expanded = if (it.gatheringType == gatheringType) !it.isExpand else it.isExpand
            it.copy(isExpand = expanded)
        }
        updateMyGathering(gatheringList)
    }

    private fun updateMyGathering(list: List<MyPageGatheringVo>) {
        viewModelScope.launch {
            myPageGatheringListStateFlow.update { list }
            isEmptyStateFlow.update { list.isEmpty() }
        }
    }

    fun readMore() {
        isExpandText = !isExpandText
        emitEventFlow(MyPageEvent.ReadMore(isExpandText))
    }

    fun goToSetting() {
        emitEventFlow(MyPageEvent.GoToSetting)
    }

    fun goToHome(){
        emitEventFlow(MyPageEvent.GoToHome)
    }

    fun goToEdit(){
        emitEventFlow(MyPageEvent.GoToEdit)
    }
}