package com.plub.presentation.ui.createGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.domain.model.state.createGathering.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.domain.model.state.createGathering.CreateGatheringPageState
import com.plub.domain.model.state.createGathering.CreateGatheringTitleAndNamePageState
import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.createGathering.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.domain.model.state.createGathering.CreateGatheringPeopleNumberPageState
import com.plub.domain.model.state.createGathering.CreateGatheringQuestionPageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
        CreateGatheringPageType.GOAL_INTRODUCE_PICTURE.idx to CreateGatheringGoalAndIntroduceAndPicturePageState(),
        CreateGatheringPageType.DAY_ON_OFF_LOCATION.idx to CreateGatheringDayAndOnOfflineAndLocationPageState(),
        CreateGatheringPageType.PEOPLE_NUMBER.idx to CreateGatheringPeopleNumberPageState(),
        CreateGatheringPageType.QUESTION.idx to CreateGatheringQuestionPageState()
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