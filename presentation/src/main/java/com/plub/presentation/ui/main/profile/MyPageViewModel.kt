package com.plub.presentation.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.enums.MyPageViewType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.model.vo.myPage.MyPageMyProfileVo
import com.plub.domain.model.vo.myPage.MyPageVo
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

    private val myPageVoStateFlow : MutableStateFlow<List<MyPageVo>> = MutableStateFlow(emptyList())

    override val uiState: MyPageState = MyPageState(
        myPageVo = myPageVoStateFlow.asStateFlow()
    )

    private var myProfileList : List<MyPageVo> = emptyList()
    private var recruitingList : List<MyPageVo> = emptyList()
    private var waitingList : List<MyPageVo> = emptyList()
    private var activeList : List<MyPageVo> = emptyList()
    private var endList : List<MyPageVo> = emptyList()
    private var isEmpty : Boolean = true


    fun setMyInfo() {
        myProfileList = listOf(
            MyPageVo(
                myPageType = MyPageViewType.PROFILE,
                myPageMyProfileVo = MyPageMyProfileVo(
                    myName = PlubUser.info.nickname,
                    myIntro = PlubUser.info.introduce,
                    profileImage = PlubUser.info.profileImage
                )
            )
        )
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
            isEmpty = false
            when(state.gatheringType){
                MyPageGatheringStateType.ACTIVE -> {
                    activeList = listOf(
                        MyPageVo(
                            myPageType = MyPageViewType.GATHERING,
                            myPageGathering = state
                        )
                    )
                }
                MyPageGatheringStateType.END -> {
                    endList = listOf(
                        MyPageVo(
                            myPageType = MyPageViewType.GATHERING,
                            myPageGathering = state
                        )
                    )
                }
                MyPageGatheringStateType.WAITING -> {
                    waitingList = listOf(
                        MyPageVo(
                            myPageType = MyPageViewType.GATHERING,
                            myPageGathering = state
                        )
                    )
                }
                MyPageGatheringStateType.RECRUITING -> {
                    recruitingList = listOf(
                        MyPageVo(
                            myPageType = MyPageViewType.GATHERING,
                            myPageGathering = state
                        )
                    )
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

    private fun updateMyGathering(list: List<MyPageVo>) {
        viewModelScope.launch {
            if(isEmpty) myPageVoStateFlow.update { myProfileList + listOf(MyPageVo(myPageType = MyPageViewType.EMPTY)) }
            else myPageVoStateFlow.update { myProfileList + list }
        }
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