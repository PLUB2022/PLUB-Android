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

    var isExpandText: Boolean = false
    val myPageGatheringVoList = mutableListOf<MyPageGatheringVo>()

    fun getMyPageData() {
        myPageGatheringVoList.clear()
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
//        handleGetMyGatheringSuccess(
//            MyPageGatheringVo(
//                isExpand = false,
//                gatheringList = arrayListOf(MyPageGatheringDetailVo(
//                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
//                    title = "테스트용 제목 1",
//                    goal =  "테스트용 목표 1",
//                    gatheringParentType = MyPageGatheringStateType.RECRUITING
//                )),
//                gatheringType = MyPageGatheringStateType.RECRUITING
//            )
//        )
//
//        handleGetMyGatheringSuccess(
//            MyPageGatheringVo(
//                isExpand = false,
//                gatheringList = arrayListOf(MyPageGatheringDetailVo(
//                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
//                    title = "테스트용 제목 1",
//                    goal =  "테스트용 목표 1",
//                    gatheringParentType = MyPageGatheringStateType.WAIT
//                )),
//                gatheringType = MyPageGatheringStateType.WAIT
//            )
//        )
//
//        handleGetMyGatheringSuccess(
//            MyPageGatheringVo(
//                isExpand = false,
//                gatheringList = arrayListOf(MyPageGatheringDetailVo(
//                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
//                    title = "테스트용 제목 1",
//                    goal =  "테스트용 목표 1",
//                    gatheringType = MyPageGatheringMyType.HOST,
//                    gatheringParentType = MyPageGatheringStateType.ACTIVE
//                ),
//                    MyPageGatheringDetailVo(
//                        image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
//                        title = "테스트용 제목 1",
//                        goal =  "테스트용 목표 1",
//                        gatheringType = MyPageGatheringMyType.GUEST,
//                        gatheringParentType = MyPageGatheringStateType.ACTIVE
//                    )),
//                gatheringType = MyPageGatheringStateType.ACTIVE
//            )
//        )
//
//        handleGetMyGatheringSuccess(
//            MyPageGatheringVo(
//                isExpand = false,
//                gatheringList = arrayListOf(MyPageGatheringDetailVo(
//                    image = "https://plub.s3.ap-northeast-2.amazonaws.com/category/workIcon.png",
//                    title = "테스트용 제목 1",
//                    goal =  "테스트용 목표 1",
//                    gatheringType = MyPageGatheringMyType.END,
//                    gatheringParentType = MyPageGatheringStateType.END
//                )),
//                gatheringType = MyPageGatheringStateType.END
//            )
//        )
//
//        updateVisibleState()
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