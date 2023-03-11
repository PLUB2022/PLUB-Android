package com.plub.presentation.ui.main.home.profile

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
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
//            getMyGatheringUseCase(MyPageGatheringStateType.RECRUITING).collect {
//                inspectUiState(it, ::handleGetMyGatheringSuccess)
//            }
//
//            getMyGatheringUseCase(MyPageGatheringStateType.WAIT).collect {
//                inspectUiState(it, ::handleGetMyGatheringSuccess)
//            }
//
//            getMyGatheringUseCase(MyPageGatheringStateType.ACTIVE).collect {
//                inspectUiState(it, ::handleGetMyGatheringSuccess)
//            }
//
//            getMyGatheringUseCase(MyPageGatheringStateType.END).collect {
//                inspectUiState(it, ::handleGetMyGatheringSuccess)
//            }
        }
        handleGetMyGatheringSuccess(
            MyPageGatheringVo(
                isExpand = false,
                gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
                    title = "테스트용 제목 1",
                    goal =  "테스트용 목표 1",
                    gatheringParentType = MyPageGatheringStateType.RECRUITING
                )),
                gatheringType = MyPageGatheringStateType.RECRUITING
            )
        )

        handleGetMyGatheringSuccess(
            MyPageGatheringVo(
                isExpand = false,
                gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
                    title = "테스트용 제목 1",
                    goal =  "테스트용 목표 1",
                    gatheringParentType = MyPageGatheringStateType.WAIT
                )),
                gatheringType = MyPageGatheringStateType.WAIT
            )
        )

        handleGetMyGatheringSuccess(
            MyPageGatheringVo(
                isExpand = false,
                gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
                    title = "테스트용 제목 1",
                    goal =  "테스트용 목표 1",
                    gatheringType = MyPageGatheringMyType.HOST,
                    gatheringParentType = MyPageGatheringStateType.ACTIVE
                ),
                    MyPageGatheringDetailVo(
                        image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
                        title = "테스트용 제목 1",
                        goal =  "테스트용 목표 1",
                        gatheringType = MyPageGatheringMyType.GUEST,
                        gatheringParentType = MyPageGatheringStateType.ACTIVE
                    )),
                gatheringType = MyPageGatheringStateType.ACTIVE
            )
        )

        handleGetMyGatheringSuccess(
            MyPageGatheringVo(
                isExpand = false,
                gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
                    title = "테스트용 제목 1",
                    goal =  "테스트용 목표 1",
                    gatheringType = MyPageGatheringMyType.END,
                    gatheringParentType = MyPageGatheringStateType.END
                )),
                gatheringType = MyPageGatheringStateType.END
            )
        )

        updateVisibleState()
    }

    private fun handleGetMyGatheringSuccess(state: MyPageGatheringVo) {

        if(state.gatheringList.isNotEmpty()){
            when(state.gatheringType){
                MyPageGatheringStateType.RECRUITING -> replaceList(FIRST_INDEX, state)
                MyPageGatheringStateType.WAIT -> replaceList(SECOND_INDEX, state)
                MyPageGatheringStateType.ACTIVE -> replaceList(THIRD_INDEX, state)
                MyPageGatheringStateType.END -> replaceList(FOURTH_INDEX, state)
            }
        }


    }

    private fun replaceList(index : Int, state : MyPageGatheringVo){
        val originList = uiState.value.myPageGatheringList
        val mutableOriginList = originList.toMutableList()

        mutableOriginList.removeAt(index)
        mutableOriginList.add(index, state)

        updateMyGathering(mutableOriginList)
    }

    private fun updateVisibleState() {
        updateUiState { uiState ->
            uiState.copy(
                isVisible = true
            )
        }
    }

    fun goToDetail(gatheringType: MyPageGatheringStateType, plubbingId : Int) {
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