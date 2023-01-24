package com.plub.presentation.ui.home.searchResult

import android.view.SearchEvent
import androidx.lifecycle.viewModelScope
import com.plub.domain.error.LoginError
import com.plub.domain.error.SearchError
import com.plub.domain.error.SignUpError
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.home.search.SearchPlubRecruitRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetSearchPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.SearchResultEvent
import com.plub.presentation.event.SearchingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    val searchPlubRecruitUseCase: GetSearchPlubRecruitUseCase
) : BaseViewModel<SearchResultPageState>(SearchResultPageState()) {

    companion object {
        private const val FIRST_PAGE = 0
    }

    private var sortType: PlubSortType = PlubSortType.NEW
    private var searchType: PlubSearchType = PlubSearchType.TITLE
    private var searchedKeyword: String = ""
    private var page: Int = FIRST_PAGE
    private var isNetworkCall: Boolean = false


    fun onSearchPlubRecruit(keyword: String) {
        viewModelScope.launch {
            page = FIRST_PAGE
            searchedKeyword = keyword
            fetchSearchPlubRecruit()
        }
    }

    fun onTabSelected(tabPosition: Int) {
        viewModelScope.launch {
            page = FIRST_PAGE
            searchType = PlubSearchType.valueOf(tabPosition)
            fetchSearchPlubRecruit()
        }
    }

    fun onClickCardType(cardType: PlubCardType) {
        viewModelScope.launch {
            page = FIRST_PAGE
            updateUiState { uiState ->
                uiState.copy(
                    cardType = cardType
                )
            }
            fetchSearchPlubRecruit()
        }
    }

    private suspend fun fetchSearchPlubRecruit() {
        val request = SearchPlubRecruitRequestVo(searchType, searchedKeyword, sortType, page)
        searchPlubRecruitUseCase(request).collect {
            inspectUiState(it, ::searchSuccess) { _, individual ->
                handleSearchError(individual as SearchError)
            }
        }
    }

    private fun searchSuccess(vo: PlubCardListVo) {
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateUiState { ui ->
            ui.copy(
                searchList = mergedList,
                totalCount = vo.totalElements,
                isEmptyViewVisible = mergedList.isEmpty()
            )
        }
        if (page++ == FIRST_PAGE) scrollToTop()
    }

    private fun scrollToTop() {
        emitEventFlow(SearchResultEvent.ScrollToTop)
    }

    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(
                viewType = uiState.value.cardType
            )
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = uiState.value.searchList
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || page == FIRST_PAGE) mappedList else originList + mappedList
    }

    private fun handleSearchError(searchError: SearchError) {
        when (searchError) {
            is SearchError.NotFoundRecruit -> {

            }

            else -> Unit
        }
    }
}
