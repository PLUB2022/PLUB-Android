package com.plub.presentation.ui.main.home.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.usecase.GetMyGatheringUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val getMyGatheringUseCase: GetMyGatheringUseCase,
) : BaseViewModel<MyPageState>(MyPageState()) {


    companion object {
        const val FIRST_INDEX = 0
        const val SECOND_INDEX = 1
        const val THIRD_INDEX = 2
        const val FOURTH_INDEX = 3
    }

    var isExpandText: Boolean = false

    fun getMyPageData() {
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

            updateVisibleState()
        }
    }

    private fun handleGetMyGatheringSuccess(state: MyPageGatheringVo) {
        val originList = uiState.value.myPageGatheringList
        val mutableOriginList = originList.toMutableList()

        if(state.gatheringList.isNotEmpty()){
            when (state.gatheringType) {
                MyPageGatheringStateType.RECRUITING -> mutableOriginList.add(FIRST_INDEX, state)
                MyPageGatheringStateType.WAIT -> mutableOriginList.add(SECOND_INDEX, state)
                MyPageGatheringStateType.ACTIVE -> mutableOriginList.add(THIRD_INDEX, state)
                MyPageGatheringStateType.END -> mutableOriginList.add(FOURTH_INDEX, state)
            }
        }

        updateMyGathering(mutableOriginList)
    }

    private fun updateVisibleState() {
        updateUiState { uiState ->
            uiState.copy(
                isVisible = true
            )
        }
    }

    fun goToDetail(gatheringType: MyPageGatheringStateType) {
        when (gatheringType) {
            MyPageGatheringStateType.RECRUITING -> {
                emitEventFlow(MyPageEvent.GoToOtherApplication)
            }
            MyPageGatheringStateType.WAIT -> {
                emitEventFlow(MyPageEvent.GoToMyApplication)
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