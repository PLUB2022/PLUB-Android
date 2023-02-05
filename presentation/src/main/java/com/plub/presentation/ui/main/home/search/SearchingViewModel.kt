package com.plub.presentation.ui.main.home.search

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.SearchError
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo
import com.plub.domain.usecase.DeleteAllRecentSearchUseCase
import com.plub.domain.usecase.DeleteRecentSearchUseCase
import com.plub.domain.usecase.FetchRecentSearchUseCase
import com.plub.domain.usecase.GetSearchPlubRecruitUseCase
import com.plub.domain.usecase.InsertRecentSearchUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingViewModel @Inject constructor(
    val searchPlubRecruitUseCase: GetSearchPlubRecruitUseCase,
    val insertRecentSearchUseCase: InsertRecentSearchUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val fetchRecentSearchUseCase: FetchRecentSearchUseCase,
    val deleteRecentSearchUseCase: DeleteRecentSearchUseCase,
    val deleteAllRecentSearchUseCase: DeleteAllRecentSearchUseCase,
) : BaseViewModel<SearchingPageState>(SearchingPageState()) {

    companion object {
        private const val FIRST_PAGE = 0
    }

    private var searchType: PlubSearchType = PlubSearchType.TITLE
    private var searchedKeyword: String = ""
    private var page: Int = FIRST_PAGE

    fun onDeleteRecentSearch(search: String) {
        viewModelScope.launch {
            deleteRecentSearchUseCase(search).collect {
                inspectUiState(it, {
                    fetchRecentSearchList()
                })
            }
        }
    }

    fun fetchRecentSearchList() {
        viewModelScope.launch {
            fetchRecentSearchUseCase(Unit).collect {
                inspectUiState(it, { list ->
                    updateRecentSearchList(list)
                })
            }
        }
    }

    fun onDeleteAllRecentSearch() {
        viewModelScope.launch {
            deleteAllRecentSearchUseCase(Unit).collect {
                inspectUiState(it, {
                    updateRecentSearchList(emptyList())
                })
            }
        }
    }

    fun onClickRecentSearch(text: String) {
        onSearchPlubRecruit(text)
    }


    fun onSearchPlubRecruit(keyword: String) {
        updateSearchText(keyword)
        viewModelScope.launch {
            insertRecentSearch(keyword) {
                viewModelScope.launch {
                    page = FIRST_PAGE
                    searchedKeyword = keyword
                    fetchSearchPlubRecruit(uiState.value.sortType)
                }
            }
        }
    }

    fun onTabSelected(tabPosition: Int) {
        viewModelScope.launch {
            page = FIRST_PAGE
            searchType = PlubSearchType.valueOf(tabPosition)
            fetchSearchPlubRecruit(uiState.value.sortType)
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
            fetchSearchPlubRecruit(uiState.value.sortType)
        }
    }

    fun onClickBookmark(id: Int) {
        viewModelScope.launch {
            postBookmark(id)
        }
    }

    fun onClickSortType(sortType: PlubSortType) {
        val menuItemType = when (sortType) {
            PlubSortType.POPULAR -> DialogMenuItemType.SORT_TYPE_POPULAR
            PlubSortType.NEW -> DialogMenuItemType.SORT_TYPE_NEW
        }

        emitEventFlow(SearchingEvent.ShowSelectSortTypeBottomSheetDialog(menuItemType))
    }

    fun onClickSortMenuItemType(item: DialogMenuItemType) {
        viewModelScope.launch {
            page = FIRST_PAGE
            val sortType = when (item) {
                DialogMenuItemType.SORT_TYPE_NEW -> PlubSortType.NEW
                DialogMenuItemType.SORT_TYPE_POPULAR -> PlubSortType.POPULAR
                else -> PlubSortType.NEW
            }
            updateSortType(sortType)
            fetchSearchPlubRecruit(sortType)
        }
    }

    fun onSearchTextChanged() {
        val searchText = uiState.value.searchText
        if (searchText.isEmpty()) {
            updateRecentSearchVisibility(true)
            updateIsSearchTextEmpty(true)
        } else {
            updateIsSearchTextEmpty(false)
        }
    }

    fun onClickDeleteSearch() {
        updateUiState { uiState ->
            uiState.copy(
                searchText = ""
            )
        }
        emitEventFlow(SearchingEvent.HideKeyboard)
    }

    private fun updateRecentSearchList(list: List<RecentSearchVo>) {
        updateUiState { uiState ->
            uiState.copy(
                recentSearchList = list,
            )
        }
    }

    private suspend fun fetchSearchPlubRecruit(sortType: PlubSortType) {
        val request = SearchPlubRecruitRequestVo(searchType, searchedKeyword, sortType, page)
        searchPlubRecruitUseCase(request).collect {
            inspectUiState(it, ::searchSuccess) { _, individual ->
                clear()
                updateRecentSearchVisibility(false)
                handleSearchError(individual as SearchError)
            }
        }
    }

    private fun searchSuccess(vo: PlubCardListVo) {
        clear()
        newSearchProcess()

        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateUiState { ui ->
            ui.copy(
                searchList = mergedList,
                isRecentSearchVisibility = false,
            )
        }
        page++
    }

    private fun newSearchProcess() {
        if (page != FIRST_PAGE) return
        emitEventFlow(SearchingEvent.ScrollToTop)
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

    private fun updateSortType(sortType: PlubSortType) {
        updateUiState { uiState ->
            uiState.copy(
                sortType = sortType
            )
        }
    }

    private fun updateRecentSearchVisibility(visibility: Boolean) {
        updateUiState { ui ->
            ui.copy(
                isRecentSearchVisibility = visibility
            )
        }
    }

    private fun updateIsSearchTextEmpty(visibility: Boolean) {
        updateUiState { ui ->
            ui.copy(
                searchTextIsEmpty = visibility,
            )
        }
    }

    private fun updateSearchText(text: String) {
        updateUiState { ui ->
            ui.copy(
                searchText = text
            )
        }
    }

    private fun clear() {
        emitEventFlow(SearchingEvent.ClearFocus)
        emitEventFlow(SearchingEvent.HideKeyboard)
    }
}
