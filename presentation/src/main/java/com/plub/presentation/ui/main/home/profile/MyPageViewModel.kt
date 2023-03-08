package com.plub.presentation.ui.main.home.profile

import com.plub.domain.model.enums.MyPageGatheringType
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel<MyPageState>(MyPageState()) {

    var isExpandText : Boolean = false

    fun goToDetail(gatheringType : MyPageGatheringType){
        when(gatheringType){
            MyPageGatheringType.RECRUITING -> { emitEventFlow(MyPageEvent.GoToOtherApplication) }
            MyPageGatheringType.WAITING -> { emitEventFlow(MyPageEvent.GoToMyApplication) }
            MyPageGatheringType.ACTIVE -> {}
            MyPageGatheringType.END -> {}
        }
    }

    fun readMore(){
        isExpandText = !isExpandText
        emitEventFlow(MyPageEvent.ReadMore(isExpandText))
    }
}