package com.plub.presentation.ui.main.home.profile

import com.plub.domain.model.enums.MyPageGatheringType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel<MyPageState>(MyPageState()) {

    var isExpandText : Boolean = false

    fun getMyPageData(){
        updateMyGathering(arrayListOf(
            MyPageGatheringVo(gatheringList = arrayListOf(
                MyPageGatheringDetailVo(
                title = "테스트용 1번",
                goal = "테스트용 1번",
                gatheringType = MyPageGatheringType.RECRUITING
            )
            ), gatheringType = MyPageGatheringType.RECRUITING),
            MyPageGatheringVo(gatheringList = arrayListOf(
                MyPageGatheringDetailVo(
                title = "테스트용 2번",
                goal = "테스트용 2번",
                gatheringType = MyPageGatheringType.WAITING
            )
            ), gatheringType = MyPageGatheringType.WAITING),
            MyPageGatheringVo(gatheringList = arrayListOf(
                MyPageGatheringDetailVo(
                title = "테스트용 3번",
                goal = "테스트용 3번",
                gatheringType = MyPageGatheringType.ACTIVE
            )
            ), gatheringType = MyPageGatheringType.ACTIVE),
            MyPageGatheringVo(gatheringList = arrayListOf(
                MyPageGatheringDetailVo(
                title = "테스트용 4번",
                goal = "테스트용 4번",
                gatheringType = MyPageGatheringType.END
            )
            ), gatheringType = MyPageGatheringType.END)))
    }

    fun goToDetail(gatheringType : MyPageGatheringType){
        when(gatheringType){
            MyPageGatheringType.RECRUITING -> { emitEventFlow(MyPageEvent.GoToOtherApplication) }
            MyPageGatheringType.WAITING -> { emitEventFlow(MyPageEvent.GoToMyApplication) }
            MyPageGatheringType.ACTIVE -> {}
            MyPageGatheringType.END -> {}
        }
    }

    fun onClickExpand(gatheringType: MyPageGatheringType){
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