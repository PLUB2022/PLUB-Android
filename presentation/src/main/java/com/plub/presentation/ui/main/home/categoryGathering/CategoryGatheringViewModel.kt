package com.plub.presentation.ui.main.home.categoryGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringBodyRequestVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringParamsVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.home.categoryGathering.filter.GatheringFilterState
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryGatheringViewModel @Inject constructor(
    val categoriesGatheringUseCase: GetCategoriesGatheringUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val resourceProvider: ResourceProvider
) : BaseViewModel<CategoryGatheringState>(CategoryGatheringState()) {

    companion object {
        private const val FIRST_PAGE = 1
    }

    private var categoryId: Int = 0
    private var pageNumber: Int = FIRST_PAGE
    private var hasMoreCards: Boolean = true
    private var isNetworkCall: Boolean = false
    private val loading = PlubCardVo(viewType = PlubCardType.LOADING)


    fun updateCategoryName(name : String){
        updateUiState { uiState->
            uiState.copy(
                categoryName = name
            )
        }
    }

    fun fetchRecommendationGatheringData(id: Int, body : GatheringFilterState) =
        viewModelScope.launch {
            categoryId = id
            isNetworkCall = true
            val paramsVo = CategoriesGatheringParamsVo(categoryId, uiState.value.sortType.key, pageNumber)
            val bodyVo = getBodyVo(body)
            categoriesGatheringUseCase(
                CategoriesGatheringRequestVo(paramsVo, bodyVo)
            ).collect { state ->
                inspectUiState(state, ::successResult)
            }
        }


    fun updateSortTypeName(sortType : PlubSortType){
        val sortTypeRes = when (sortType) {
            PlubSortType.POPULAR -> R.string.word_sort_type_popular
            PlubSortType.NEW -> R.string.word_sort_type_new
        }

        updateUiState { uiState ->
            uiState.copy(
                sortTypeName = resourceProvider.getString(sortTypeRes)
            )
        }
    }

    private fun getBodyVo(body : GatheringFilterState) : CategoriesGatheringBodyRequestVo{
        val days = if(body.gatheringDays.isEmpty() || body.gatheringDays.contains(DaysType.ALL)) null else body.gatheringDays.map { it.eng }
        val subCategoryId = if(body.hobbiesSelectedVo.hobbies.isEmpty()) null else getMergeSelectedHobbyList(body.hobbiesSelectedVo.hobbies)
        val accountNum = if(body.accountNum == 0) null else body.accountNum

        return CategoriesGatheringBodyRequestVo(
            days = days,
            subCategoryId = subCategoryId,
            accountNum = accountNum
        )
    }

    private fun getMergeSelectedHobbyList(list : List<SelectedHobbyVo>) : List<Int>{
        val subCategoryIdList = mutableListOf<Int>()
        for(content in list){
            subCategoryIdList.add(content.subId)
        }

        return subCategoryIdList
    }

    private fun fetchRecommendationGatheringData() =
        viewModelScope.launch {
            isNetworkCall = true
            val paramsVo = CategoriesGatheringParamsVo(categoryId, uiState.value.sortType.key, pageNumber)
            val bodyVo = CategoriesGatheringBodyRequestVo()
            categoriesGatheringUseCase(
                CategoriesGatheringRequestVo(paramsVo, bodyVo)
            ).collect { state ->
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
                cardList = if(hasMoreCards) mergedList else mergedList,
                isEmptyViewVisible = mergedList.isEmpty()
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
        val originList =  uiState.value.cardList
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || pageNumber == FIRST_PAGE)  mappedList else originList + mappedList
    }

    fun scrollTop() {
        if (pageNumber == FIRST_PAGE) {
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

    fun goToDetailRecruitment(id: Int, isHost: Boolean) {
        if (isHost) {
            emitEventFlow(CategoryGatheringEvent.GoToHostRecruit(id))
        } else {
            emitEventFlow(CategoryGatheringEvent.GoToRecruit(id))
        }
    }

    fun clickSearch() {
        emitEventFlow(CategoryGatheringEvent.GoToSearch)
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (!isNetworkCall && isBottom && isDownScroll && hasMoreCards) {
            fetchRecommendationGatheringData()
        }
    }

    fun goToFilter() {
        emitEventFlow(CategoryGatheringEvent.GoToFilter)
    }
}