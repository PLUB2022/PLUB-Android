package com.plub.presentation.ui.main.home.bookmark

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetMyPlubBookmarksUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val getMyPlubBookmarksUseCase: GetMyPlubBookmarksUseCase
) : BaseTestViewModel<BookmarkPageState>() {

    companion object {
        private const val FIRST_PAGE = 0
    }

    private val bookmarkListStateFlow:MutableStateFlow<List<PlubCardVo>> = MutableStateFlow(emptyList())
    private val cardTypeStateFlow:MutableStateFlow<PlubCardType> = MutableStateFlow(PlubCardType.LIST)
    private val isEmptyViewModeStateFlow:MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: BookmarkPageState = BookmarkPageState(
        bookmarkListStateFlow.asStateFlow(),
        cardTypeStateFlow.asStateFlow(),
        isEmptyViewModeStateFlow.asStateFlow()
    )

    private var page: Int = FIRST_PAGE

    fun onClickCardType(cardType: PlubCardType) {
        page = FIRST_PAGE
        updateCardType(cardType)
        getPlubBookmarks()
    }

    fun onGetPlubBookmark() {
        if(bookmarkListStateFlow.value.isNotEmpty()) return
        page = FIRST_PAGE
        getPlubBookmarks()
    }

    fun onClickBookmark(id: Int) {
        postBookmark(id)
    }

    private fun getPlubBookmarks() {
        viewModelScope.launch {
            getMyPlubBookmarksUseCase(Unit).collect {
                inspectUiState(it, ::fetchPlubBookmarksSuccess)
            }
        }
    }

    private fun fetchPlubBookmarksSuccess(vo: PlubCardListVo) {
        newFetchProcess()
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateBookmarkList(mergedList)
        updateIsEmptyViewMode(mergedList.isEmpty())
        page++
    }

    private fun newFetchProcess() {
        if (page == FIRST_PAGE) return
        emitEventFlow(BookmarksEvent.ScrollToTop)
    }

    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(viewType = cardTypeStateFlow.value)
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = bookmarkListStateFlow.value
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || page == FIRST_PAGE) mappedList else originList + mappedList
    }

    private fun postBookmark(id: Int) {
        viewModelScope.launch {
            postBookmarkPlubRecruitUseCase(id).collect {
                inspectUiState(it, ::postBookmarkSuccess)
            }
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = bookmarkListStateFlow.value
        val newList = list.map {
            val bookmark = if (it.id == vo.id) vo.isBookmarked else it.isBookmarked
            it.copy(isBookmarked = bookmark)
        }
        updateBookmarkList(newList)
    }

    fun onClickBack(){
        emitEventFlow(BookmarksEvent.GoToBack)
    }

    private fun updateBookmarkList(list:List<PlubCardVo>) {
        viewModelScope.launch {
            bookmarkListStateFlow.update { list }
        }
    }

    private fun updateCardType(type:PlubCardType) {
        viewModelScope.launch {
            cardTypeStateFlow.update { type }
        }
    }

    private fun updateIsEmptyViewMode(isEmpty:Boolean) {
        viewModelScope.launch {
            isEmptyViewModeStateFlow.update { isEmpty }
        }
    }
}
