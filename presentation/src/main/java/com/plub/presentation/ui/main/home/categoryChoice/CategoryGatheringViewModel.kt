package com.plub.presentation.ui.main.home.categoryChoice

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryGatheringViewModel @Inject constructor(
    val categoriesGatheringUseCase: GetCategoriesGatheringUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase
) : BaseViewModel<CategoryGatheringState>(CategoryGatheringState()) {

    companion object {
        private const val FIRST_PAGE = 1
    }

    private var categoryId: Int = 0
    private var pageNumber: Int = FIRST_PAGE
    private var hasMoreCards: Boolean = true
    private var isNetworkCall : Boolean = false


    fun fetchRecommendationGatheringData(id: Int) =
        viewModelScope.launch {
            categoryId = id
            isNetworkCall = true
            categoriesGatheringUseCase(
                CategoriesGatheringRequestVo(
                    categoryId,
                    uiState.value.sortType.key,
                    pageNumber
                )
            ).collect { state ->
                inspectUiState(state, ::successResult)
            }
        }

    private fun fetchRecommendationGatheringData() =
        viewModelScope.launch {
            isNetworkCall = true
            val request = CategoriesGatheringRequestVo(
                categoryId,
                uiState.value.sortType.key,
                pageNumber
            )
            categoriesGatheringUseCase(request).collect { state ->
                inspectUiState(state, ::successResult)
            }
        }

    private fun successResult(vo: PlubCardListVo) {
        isNetworkCall = false
        hasMoreCards = (pageNumber != vo.totalPages)
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateUiState { ui ->
            ui.copy(
                cardList = mergedList,
                isEmptyViewVisible = mergedList.isEmpty(),
                isLoading = hasMoreCards
            )
        }
        pageNumber++
    }

    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(
                viewType = uiState.value.cardType
            )
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = uiState.value.cardList
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || pageNumber == FIRST_PAGE) {
            mappedList
        } else {
            originList + mappedList
        }
    }

    fun scrollTop(){
        if(pageNumber == FIRST_PAGE){
            emitEventFlow(CategoryGatheringEvent.ScrollTop)
        }
    }


    fun clickBookmark(plubbingId: Int) {
        viewModelScope.launch {
            postBookmarkPlubRecruitUseCase(plubbingId).collect {
                inspectUiState(it, ::postBookmarkSuccess)
            }
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = uiState.value.cardList
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
                cardList = list
            )
        }
    }

    fun onClickCardType(cardType: PlubCardType) {
        pageNumber = FIRST_PAGE
        viewModelScope.launch {
            updateUiState { uiState ->
                uiState.copy(
                    cardType = cardType
                )
            }
            fetchRecommendationGatheringData()
        }
    }

    fun backPage() {
        emitEventFlow(CategoryGatheringEvent.GoToBack)
    }

    fun goToCreate() {
        emitEventFlow(CategoryGatheringEvent.GoToCreate)
    }

    fun onClickSortMenuItemType(item: DialogMenuItemType) {
        viewModelScope.launch {
            pageNumber = FIRST_PAGE
            val sortType = when (item) {
                DialogMenuItemType.SORT_TYPE_NEW -> PlubSortType.NEW
                DialogMenuItemType.SORT_TYPE_POPULAR -> PlubSortType.POPULAR
                else -> PlubSortType.POPULAR
            }
            updateSortType(sortType)
            fetchRecommendationGatheringData()
        }
    }

    private fun updateSortType(sortType: PlubSortType) {
        updateUiState { uiState ->
            uiState.copy(
                sortType = sortType
            )
        }
    }

    fun onClickSortType(sortType: PlubSortType) {
        val menuItemType = when (sortType) {
            PlubSortType.POPULAR -> DialogMenuItemType.SORT_TYPE_POPULAR
            PlubSortType.NEW -> DialogMenuItemType.SORT_TYPE_NEW
        }

        emitEventFlow(CategoryGatheringEvent.ShowSelectSortTypeBottomSheetDialog(menuItemType))
    }

    fun goToDetailRecruitment(id : Int){
        emitEventFlow(CategoryGatheringEvent.GoToRecruit(id))
    }

    fun clickSearch() {
        emitEventFlow(CategoryGatheringEvent.GoToSearch)
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (!isNetworkCall && isBottom && isDownScroll && hasMoreCards) {
            fetchRecommendationGatheringData()
        }
    }
}