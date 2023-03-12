package com.plub.presentation.ui.main.home.profile.active

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageActiveDetailViewType
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.myPage.MyPageActiveDetailVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.domain.usecase.GetMyPostUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveGatheringViewModel @Inject constructor(
    private val getMyPostUseCase: GetMyPostUseCase,
    private val fetchPlubingMainUseCase: FetchPlubingMainUseCase
) : BaseViewModel<ActiveGatheringPageState>(ActiveGatheringPageState()) {

    companion object {
        const val FIRST_CURSOR = 0
        const val MAX_POST_COUNT = 3
    }

    private var cursorId: Int = FIRST_CURSOR
    private var plubingId: Int = 0
    private var gatheringMyType : MyPageGatheringMyType = MyPageGatheringMyType.END

    fun setPlubIdAndStateType(id: Int, type: MyPageGatheringMyType) {
        plubingId = id
        gatheringMyType = type
    }

    fun setTopView() {
        viewModelScope.launch {
            fetchPlubingMainUseCase(plubingId).collect {
                inspectUiState(it, ::onSuccessPlubingMainInfo)
            }
        }
    }

    private fun onSuccessPlubingMainInfo(mainVo: PlubingMainVo) {
        val topView = MyPageDetailTitleVo(
            title = mainVo.name,
            date = mainVo.days,
            position = mainVo.placeName,
            time = mainVo.time,
            viewType = gatheringMyType
        )

        updateUiState { uiState ->
            uiState.copy(
                detailList = getMergedTopList(topView)
            )
        }
    }

    private fun getMergedTopList(view : MyPageDetailTitleVo) : List<MyPageActiveDetailVo>{
        return arrayListOf(
            MyPageActiveDetailVo(
                viewType = MyPageActiveDetailViewType.TOP,
                title = view
            )
        )
    }

    fun getMyPost() {
        viewModelScope.launch {
            getMyPostUseCase(MyPageActiveRequestVo(plubingId, cursorId)).collect {
                inspectUiState(it, ::handlegetMyPostSuccess)
            }
        }
    }

    private fun handlegetMyPostSuccess(state: PlubingBoardListVo) {
        if (state.totalElements > MAX_POST_COUNT) {
            setListOverBoardMaxCount(state.content)
        } else {
            setListUnderBoardMaxCount(state.content)
        }
    }

    private fun setListOverBoardMaxCount(list: List<PlubingBoardVo>) {
        val contentList = mutableListOf<PlubingBoardVo>()
        for (index in 0..MAX_POST_COUNT) {
            if (index == MAX_POST_COUNT) {
                break
            }

            contentList.add(list[index])
        }

    }

    private fun setListUnderBoardMaxCount(list: List<PlubingBoardVo>) {

    }


}