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
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
) : BaseTestViewModel<SearchingPageState>() {

    companion object {
        private const val FIRST_PAGE = 0
    }

    private val isRecentSearchModeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val recentSearchListStateFlow: MutableStateFlow<List<RecentSearchVo>> = MutableStateFlow(emptyList())
    private val searchListStateFlow: MutableStateFlow<List<PlubCardVo>> = MutableStateFlow(emptyList())
    private val cardTypeStateFlow: MutableStateFlow<PlubCardType> = MutableStateFlow(PlubCardType.LIST)
    private val sortTypeStateFlow: MutableStateFlow<PlubSortType> = MutableStateFlow(PlubSortType.POPULAR)
    private val isSearchedTextEmptyStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var searchTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: SearchingPageState = SearchingPageState(
        isRecentSearchModeStateFlow.asStateFlow(),
        recentSearchListStateFlow.asStateFlow(),
        searchListStateFlow.asStateFlow(),
        cardTypeStateFlow.asStateFlow(),
        sortTypeStateFlow.asStateFlow(),
        isSearchedTextEmptyStateFlow.asStateFlow(),
        searchTextStateFlow
    )

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
        updateIsSearchedTextEmpty(keyword.isEmpty())
        insertRecentSearch(keyword) {
            page = FIRST_PAGE
            searchedKeyword = keyword
            getSearchPlubRecruit(sortTypeStateFlow.value)
        }
    }

    fun onTabSelected(tabPosition: Int) {
        page = FIRST_PAGE
        searchType = PlubSearchType.valueOf(tabPosition)
        getSearchPlubRecruit(sortTypeStateFlow.value)
    }

    fun onClickCardType(cardType: PlubCardType) {
        page = FIRST_PAGE
        updateCardType(cardType)
        getSearchPlubRecruit(sortTypeStateFlow.value)
    }

    fun onClickBookmark(id: Int) {
        postBookmark(id)
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
            getSearchPlubRecruit(sortType)
        }
    }

    fun onSearchTextChanged() {
        val searchText = searchTextStateFlow.value
        if (searchText.isEmpty()) {
            updateIsRecentSearchMode(true)
            updateIsSearchedTextEmpty(true)
        }
    }

    fun onClickDeleteSearch() {
        updateSearchText("")
        emitEventFlow(SearchingEvent.HideKeyboard)
    }

    fun onClickBack() {
        emitEventFlow(SearchingEvent.GoToBack)
    }

    fun goToDetailRecruitment(id: Int) {
        emitEventFlow(SearchingEvent.GoToRecruit(id))
    }

    private fun getSearchPlubRecruit(sortType: PlubSortType) {
        val request = SearchPlubRecruitRequestVo(searchType, searchedKeyword, sortType, page)
        viewModelScope.launch {
            searchPlubRecruitUseCase(request).collect {
                inspectUiState(it, ::searchSuccess) { _, individual ->
                    clear()
                    updateIsRecentSearchMode(false)
                    handleSearchError(individual as SearchError)
                }
            }
        }
    }

    private fun searchSuccess(vo: PlubCardListVo) {
        clear()
        newSearchProcess()

        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateSearchList(mergedList)
        updateIsRecentSearchMode(false)
        page++
    }

    private fun newSearchProcess() {
        if (page != FIRST_PAGE) return
        emitEventFlow(SearchingEvent.ScrollToTop)
    }

    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(
                viewType = cardTypeStateFlow.value
            )
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = searchListStateFlow.value
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

    private fun insertRecentSearch(text: String, insertSuccess: () -> Unit) {
        val request = RecentSearchVo(search = text, saveTime = System.currentTimeMillis())
        viewModelScope.launch {
            insertRecentSearchUseCase(request).collect {
                inspectUiState(it, {
                    insertSuccess.invoke()
                })
            }
        }
    }

    private fun postBookmark(id: Int) {
        viewModelScope.launch {
            postBookmarkPlubRecruitUseCase(id).collect {
                inspectUiState(it, ::postBookmarkSuccess)
            }
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = searchListStateFlow.value
        val newList = list.map {
            val bookmark = if (it.id == vo.id) vo.isBookmarked else it.isBookmarked
            it.copy(
                isBookmarked = bookmark
            )
        }
        updateSearchList(newList)
    }

    private fun clear() {
        emitEventFlow(SearchingEvent.ClearFocus)
        emitEventFlow(SearchingEvent.HideKeyboard)
    }

    private fun updateIsRecentSearchMode(isRecentSearchMode: Boolean) {
        viewModelScope.launch {
            isRecentSearchModeStateFlow.update { isRecentSearchMode }
        }
    }

    private fun updateRecentSearchList(list: List<RecentSearchVo>) {
        viewModelScope.launch {
            recentSearchListStateFlow.update { list }
        }
    }

    private fun updateSearchList(list: List<PlubCardVo>) {
        viewModelScope.launch {
            searchListStateFlow.update { list }
        }
    }

    private fun updateCardType(type: PlubCardType) {
        viewModelScope.launch {
            cardTypeStateFlow.update { type }
        }
    }

    private fun updateSortType(type: PlubSortType) {
        viewModelScope.launch {
            sortTypeStateFlow.update { type }
        }
    }

    private fun updateIsSearchedTextEmpty(isEmpty: Boolean) {
        viewModelScope.launch {
            isSearchedTextEmptyStateFlow.update { isEmpty }
        }
    }

    private fun updateSearchText(text: String) {
        viewModelScope.launch {
            searchTextStateFlow.update { text }
        }
    }
}
