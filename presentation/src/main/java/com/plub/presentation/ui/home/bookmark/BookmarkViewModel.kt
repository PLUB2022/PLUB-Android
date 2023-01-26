package com.plub.presentation.ui.home.bookmark

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetMyPlubBookmarksUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.BookmarksEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val getMyPlubBookmarksUseCase: GetMyPlubBookmarksUseCase
) : BaseViewModel<BookmarkPageState>(BookmarkPageState()) {

    companion object {
        private const val FIRST_PAGE = 0
    }

    private var page: Int = FIRST_PAGE

    fun onClickCardType(cardType: PlubCardType) {
        viewModelScope.launch {
            page = FIRST_PAGE
            updateUiState { uiState ->
                uiState.copy(
                    cardType = cardType
                )
            }
            fetchPlubBookmarks()
        }
    }

    fun onFetchPlubBookmark() {
        viewModelScope.launch {
            page = FIRST_PAGE
            fetchPlubBookmarks()
        }
    }

    fun onClickBookmark(id: Int) {
        viewModelScope.launch {
            postBookmark(id)
        }
    }

    private suspend fun fetchPlubBookmarks() {
        getMyPlubBookmarksUseCase(Unit).collect {
            inspectUiState(it, ::fetchPlubBookmarksSuccess)
        }
    }

    private fun fetchPlubBookmarksSuccess(vo: PlubCardListVo) {
        newFetchProcess()
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateUiState { ui ->
            ui.copy(
                bookmarkList = mergedList,
                isEmptyViewVisible = mergedList.isEmpty()
            )
        }
        page++
    }

    private fun newFetchProcess() {
        if (page == FIRST_PAGE) return
        emitEventFlow(BookmarksEvent.ScrollToTop)
    }

    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(
                viewType = uiState.value.cardType
            )
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = uiState.value.bookmarkList
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || page == FIRST_PAGE) mappedList else originList + mappedList
    }

    private suspend fun postBookmark(id: Int) {
        postBookmarkPlubRecruitUseCase(id).collect {
            inspectUiState(it, ::postBookmarkSuccess)
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = uiState.value.bookmarkList
        val newList = list.map {
            val bookmark = if (it.id == vo.id) vo.isBookmarked else it.isBookmarked
            it.copy(
                isBookmarked = bookmark
            )
        }
        updateBookmarkList(newList)
    }

    private fun updateBookmarkList(list: List<PlubCardVo>) {
        updateUiState { uiState ->
            uiState.copy(
                bookmarkList = list
            )
        }
    }
}
