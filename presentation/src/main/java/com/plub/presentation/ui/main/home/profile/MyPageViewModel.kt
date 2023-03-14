package com.plub.presentation.ui.main.home.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.usecase.GetMyGatheringUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val getMyGatheringUseCase: GetMyGatheringUseCase,
) : BaseViewModel<MyPageState>(MyPageState()) {

    private var isExpandText: Boolean = false
    private var isFirstInit : Boolean = true
    private val myPageGatheringVoList = mutableListOf<MyPageGatheringVo>()

    fun setMyInfo(){
        updateUiState { uiState ->
            uiState.copy(
                myName = PlubUser.info.nickname,
                myIntro = PlubUser.info.introduce,
                profileImage = PlubUser.info.profileImage
            )
        }
    }

    fun getMyPageData() {
        if(isFirstInit) {
            viewModelScope.launch {
                getMyGatheringUseCase(MyPageGatheringStateType.RECRUITING).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }

                getMyGatheringUseCase(MyPageGatheringStateType.WAIT).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }

                getMyGatheringUseCase(MyPageGatheringStateType.ACTIVE).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }

                getMyGatheringUseCase(MyPageGatheringStateType.END).collect {
                    inspectUiState(it, ::handleGetMyGatheringSuccess)
                }
                updateMyGathering(myPageGatheringVoList)
            }
            isFirstInit = false
        }
    }

    private fun handleGetMyGatheringSuccess(state: MyPageGatheringVo) {
        if(state.gatheringList.isNotEmpty()){
            myPageGatheringVoList.add(state)
        }
    }

    fun goToDetail(gatheringType: MyPageGatheringStateType, plubbingId: Int) {
        when (gatheringType) {
            MyPageGatheringStateType.RECRUITING -> {
                emitEventFlow(MyPageEvent.GoToOtherApplication(plubbingId))
            }
            MyPageGatheringStateType.WAIT -> {
                emitEventFlow(MyPageEvent.GoToMyApplication(plubbingId))
            }
            MyPageGatheringStateType.ACTIVE -> {}
            MyPageGatheringStateType.END -> {}
            else -> {}
        }
    }

    fun onClickExpand(gatheringType: MyPageGatheringStateType) {
        val gatheringList = uiState.value.myPageGatheringList.map {
            val expanded = if (it.gatheringType == gatheringType) !it.isExpand else it.isExpand
            it.copy(isExpand = expanded)
        }
        updateMyGathering(gatheringList)
    }

    private fun updateMyGathering(list: List<MyPageGatheringVo>) {
        updateUiState { uiState ->
            uiState.copy(
                myPageGatheringList = list
            )
        }
    }

    fun readMore() {
        isExpandText = !isExpandText
        emitEventFlow(MyPageEvent.ReadMore(isExpandText))
    }

    fun goToSetting() {
        emitEventFlow(MyPageEvent.GoToSetting)
    }
}