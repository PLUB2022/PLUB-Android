package com.plub.presentation.ui.createGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.domain.model.state.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.domain.model.state.CreateGatheringPageState
import com.plub.domain.model.state.CreateGatheringTitleAndNamePageState
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringPageState>(CreateGatheringPageState()) {

    private var currentPage = 0
    private val maxPage = CreateGatheringPageType.values().size - 1

    private val childrenPageStateMap: MutableMap<Int, PageState> = mutableMapOf(
        CreateGatheringPageType.SELECT_PLUB_CATEGORY.idx to PageState.Default,
        CreateGatheringPageType.GATHERING_TITLE_AND_NAME.idx to CreateGatheringTitleAndNamePageState(),
        CreateGatheringPageType.GOAL_INTRODUCE_PICTURE.idx to CreateGatheringGoalAndIntroduceAndPicturePageState()
    )

    private val _childrenPageStateFlow
        = MutableStateFlow<PageState>(PageState.Default)
    val childrenPageStateFlow: SharedFlow<PageState> = _childrenPageStateFlow.asStateFlow()

    fun onMoveToNextPage(pageState: PageState) {
        if (isLastPage()) return
        setChildrenPageState(pageState)
        updateUiState { uiState ->
            uiState.copy(currentPage = ++currentPage)
        }
        emitChildrenPageState(currentPage)
    }

    private fun setChildrenPageState(pageState: PageState) {
        childrenPageStateMap[currentPage] = pageState
    }

    private fun emitChildrenPageState(idx: Int) {
        viewModelScope.launch {
            val childrenPageState = childrenPageStateMap[idx] ?: PageState.Default
            _childrenPageStateFlow.emit(childrenPageState)
        }
    }

    fun isFirstPage() = currentPage == 0
    fun isLastPage() = currentPage == maxPage
}