package com.plub.presentation.ui.main.home.profile.active

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.usecase.GetMyPostUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.plubing.board.PlubingBoardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveGatheringViewModel @Inject constructor(
    private val getMyPostUseCase: GetMyPostUseCase
) : BaseViewModel<ActiveGatheringPageState>(ActiveGatheringPageState()) {

    companion object{
        const val FIRST_PAGE = 1
        const val MAX_POST_COUNT = 3
    }

    private var cursorId : Int = FIRST_PAGE

    fun getMyPost(plubbingId : Int) {
        viewModelScope.launch {
            getMyPostUseCase(MyPageActiveRequestVo(plubbingId, cursorId)).collect{
                inspectUiState(it, ::handlegetMyPostSuccess)
            }
        }
    }

    private fun handlegetMyPostSuccess(state : PlubingBoardListVo){
        if(state.totalElements > MAX_POST_COUNT){
            setListOverBoardMaxCount(state.content)
        }
        else{
            setListUnderBoardMaxCount(state.content)
        }
    }

    private fun setListOverBoardMaxCount(list: List<PlubingBoardVo>){
        val contentList = mutableListOf<PlubingBoardVo>()
        for(index in 0..MAX_POST_COUNT){
            if(index == MAX_POST_COUNT){
                break
            }

            contentList.add(list[index])
        }
        val mergedList = getMergeList(contentList)
        updateBoardList(mergedList)
    }

    private fun setListUnderBoardMaxCount(list: List<PlubingBoardVo>){
        val mergedList = getMergeList(list)
        updateBoardList(mergedList)
    }

    private fun updateBoardList(list:List<PlubingBoardVo>) {
        updateUiState { uiState ->
            uiState.copy(
                boardList = list
            )
        }
    }


    private fun getMergeList(list: List<PlubingBoardVo>): List<PlubingBoardVo> {
        val originList = uiState.value.boardList
        return if (cursorId == FIRST_PAGE) list else originList + list
    }
}