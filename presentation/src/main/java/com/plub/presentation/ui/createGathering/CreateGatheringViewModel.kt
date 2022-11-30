package com.plub.presentation.ui.createGathering

import com.plub.domain.model.state.CreateGatheringPageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ViewPager
import com.plub.presentation.util.ViewPager.CREATE_GATHERING_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringViewModel @Inject constructor() : BaseViewModel<CreateGatheringPageState>(CreateGatheringPageState()) {

    private var currentPage = 0
    private val maxPage = CREATE_GATHERING_PAGE_SIZE - 1

    val isLastPage: Boolean
        get() = currentPage == maxPage

    val isFirstPage: Boolean
        get() = currentPage == 0

    fun onClickNextButton() {
        if(isLastPage) return

        updateUiState { uiState ->
            uiState.copy(currentPage = ++currentPage)
        }
    }
}