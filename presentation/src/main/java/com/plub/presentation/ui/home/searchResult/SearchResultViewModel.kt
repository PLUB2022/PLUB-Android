package com.plub.presentation.ui.home.searchResult

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.SearchError
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo
import com.plub.domain.usecase.GetSearchPlubRecruitUseCase
import com.plub.domain.usecase.InsertRecentSearchUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.SearchResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    val searchPlubRecruitUseCase: GetSearchPlubRecruitUseCase,
    val insertRecentSearchUseCase: InsertRecentSearchUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
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
            insertRecentSearch(keyword) {
                viewModelScope.launch {
                    page = FIRST_PAGE
                    searchedKeyword = keyword
                    fetchSearchPlubRecruit()
                }
            }
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

    fun onClickBookmark(id: Int) {
        viewModelScope.launch {
            postBookmark(id)
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
        newSearchProcess()
        emitEventFlow(SearchResultEvent.HideKeyboard)
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateUiState { ui ->
            ui.copy(
                searchList = mergedList,
                totalCount = vo.totalElements,
                isEmptyViewVisible = mergedList.isEmpty()
            )
        }
        page++
    }

    private fun newSearchProcess() {
        if (page == FIRST_PAGE) return
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

    private suspend fun insertRecentSearch(text: String, insertSuccess: () -> Unit) {
        val request = RecentSearchVo(search = text, saveTime = System.currentTimeMillis())
        insertRecentSearchUseCase(request).collect {
            inspectUiState(it, {
                insertSuccess.invoke()
            })
        }
    }

    private suspend fun postBookmark(id: Int) {
        postBookmarkPlubRecruitUseCase(id).collect {
            inspectUiState(it, ::postBookmarkSuccess)
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = uiState.value.searchList
        val newList = list.map {
            val bookmark = if (it.id == vo.id) vo.isBookmarked else it.isBookmarked
            it.copy(
                isBookmarked = bookmark
            )
        }
        updateSearchList(newList)
    }

    private fun updateSearchList(list: List<PlubCardVo>) {
        updateUiState { uiState ->
            uiState.copy(
                searchList = list
            )
        }
    }
}