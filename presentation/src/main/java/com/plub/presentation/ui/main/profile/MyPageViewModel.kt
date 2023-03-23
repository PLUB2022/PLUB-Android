package com.plub.presentation.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.usecase.GetMyGatheringUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val getMyGatheringUseCase: GetMyGatheringUseCase,
) : BaseViewModel<MyPageState>(MyPageState()) {

    companion object {
        const val MAX_LENGTH = 15
    }

    private var isExpandText: Boolean = false

    fun setMyInfo() {

        updateUiState { uiState ->
            uiState.copy(
                myName = PlubUser.info.nickname,
                myIntro = PlubUser.info.introduce,
                profileImage = PlubUser.info.profileImage,
                isReadMore = PlubUser.info.introduce.length > MAX_LENGTH
            )
        }
    }

    fun refresh(){
        updateMyGathering(emptyList())
    }

    fun getMyPageData() {
        viewModelScope.launch {
            getMyGatheringUseCase(MyPageGatheringStateType.RECRUITING).collect {
                inspectUiState(it, ::handleGetMyGatheringSuccess)
            }

            getMyGatheringUseCase(MyPageGatheringStateType.WAITING).collect {
                inspectUiState(it, ::handleGetMyGatheringSuccess)
            }

            getMyGatheringUseCase(MyPageGatheringStateType.ACTIVE).collect {
                inspectUiState(it, ::handleGetMyGatheringSuccess)
            }

            getMyGatheringUseCase(MyPageGatheringStateType.END).collect {
                inspectUiState(it, ::handleGetMyGatheringSuccess)
            }
        }
    }

    private fun handleGetMyGatheringSuccess(state: MyPageGatheringVo) {
        if (state.gatheringList.isNotEmpty()) {
            val mutableOriginList = uiState.value.myPageGatheringList.toMutableList()
            mutableOriginList.add(state)
            updateMyGathering(mutableOriginList)
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