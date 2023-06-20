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
        private const val FIRST_CURSOR = 0
    }

    private val bookmarkListStateFlow:MutableStateFlow<List<PlubCardVo>> = MutableStateFlow(emptyList())
    private val cardTypeStateFlow:MutableStateFlow<PlubCardType> = MutableStateFlow(PlubCardType.LIST)
    private val isEmptyViewModeStateFlow:MutableStateFlow<Boolean> = MutableStateFlow(true)

    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false
    private var cursorId: Int = FIRST_CURSOR

    override val uiState: BookmarkPageState = BookmarkPageState(
        bookmarkListStateFlow.asStateFlow(),
        cardTypeStateFlow.asStateFlow(),
        isEmptyViewModeStateFlow.asStateFlow()
    )

    fun onClickCardType(cardType: PlubCardType) {
        cursorId = FIRST_CURSOR
        isNetworkCall = true
        isLastPage = false
        updateCardType(cardType)
        getPlubBookmarks(showLoading = true)
    }

    fun onGetPlubBookmark() {
        if(bookmarkListStateFlow.value.isNotEmpty()) return
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getPlubBookmarks(showLoading = true)
    }

    fun onClickBookmark(id: Int) {
        postBookmark(id)
    }

    fun onScrollChanged() {
        if (!isLastPage && !isNetworkCall) onGetNextPlubBookmark()
    }

    fun goToDetailRecruitment(id: Int) {
        emitEventFlow(BookmarksEvent.GoToRecruit(id))
    }

    private fun onGetNextPlubBookmark() {
        isNetworkCall = true
        cursorUpdate()
        getPlubBookmarks(showLoading = false)
    }

    private fun getPlubBookmarks(showLoading : Boolean) {
        viewModelScope.launch {
            getMyPlubBookmarksUseCase(cursorId).collect {
                inspectUiState(it, ::fetchPlubBookmarksSuccess, needShowLoading = showLoading)
            }
        }
    }

    private fun fetchPlubBookmarksSuccess(vo: PlubCardListVo) {
        newFetchProcess()
        isLastPage = vo.last
        val mappedList = mapToCardType(vo.content)
        val mergedList = if(isLastPage) getMergeList(mappedList) else getMergeList(mappedList) + listOf(PlubCardVo(viewType = PlubCardType.LOADING))
        updateBookmarkList(mergedList)
        updateIsEmptyViewMode(mergedList.isEmpty())
        isNetworkCall = false
    }

    private fun newFetchProcess() {
        if (cursorId == FIRST_CURSOR) emitEventFlow(BookmarksEvent.ScrollToTop)
    }

    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(viewType = cardTypeStateFlow.value)
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = bookmarkListStateFlow.value.filterNot { it.viewType == PlubCardType.LOADING }
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || cursorId == FIRST_CURSOR) mappedList else originList + mappedList
    }

    private fun cursorUpdate() {
        cursorId = if (bookmarkListStateFlow.value.isEmpty()) FIRST_CURSOR
        else bookmarkListStateFlow.value.filterNot { it.viewType == PlubCardType.LOADING }.lastOrNull()?.id ?: FIRST_CURSOR
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
