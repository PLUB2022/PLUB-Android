package com.plub.presentation.ui.main.home.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.usecase.GetMyGatheringUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val getMyGatheringUseCase: GetMyGatheringUseCase,
) : BaseViewModel<MyPageState>(MyPageState()) {

    var isExpandText : Boolean = false

    fun getMyPageData(){
        viewModelScope.launch {
            getMyGatheringUseCase(MyPageGatheringStateType.ACTIVE).collect{
                inspectUiState(it, ::handleGetMyGatheringSuccess)
            }
        }
    }

    private fun handleGetMyGatheringSuccess(state : MyPageGatheringVo){
        when(state.gatheringType){
            MyPageGatheringStateType.ACTIVE -> TODO()
            MyPageGatheringStateType.END -> TODO()
            MyPageGatheringStateType.DELETED -> TODO()
            MyPageGatheringStateType.PAUSE -> TODO()
        }
    }

    fun goToDetail(gatheringType : MyPageGatheringStateType){
        when(gatheringType){
            //MyPageGatheringStateType.RECRUITING -> { emitEventFlow(MyPageEvent.GoToOtherApplication) }
            //MyPageGatheringStateType.WAITING -> { emitEventFlow(MyPageEvent.GoToMyApplication) }
            MyPageGatheringStateType.ACTIVE -> {}
            MyPageGatheringStateType.END -> {}
            else -> {}
        }
    }

    fun onClickExpand(gatheringType: MyPageGatheringStateType){
        val gatheringList = uiState.value.myPageGatheringList.map {
            val expanded = if(it.gatheringType == gatheringType) !it.isExpand else it.isExpand
            it.copy(isExpand = expanded)
        }
        updateMyGathering(gatheringList)
    }

    private fun updateMyGathering(list : List<MyPageGatheringVo>){
        updateUiState { uiState ->
            uiState.copy(
                myPageGatheringList = list
            )
        }
    }

    fun readMore(){
        isExpandText = !isExpandText
        emitEventFlow(MyPageEvent.ReadMore(isExpandText))
    }

    fun goToSetting(){
        emitEventFlow(MyPageEvent.GoToSetting)
    }
}