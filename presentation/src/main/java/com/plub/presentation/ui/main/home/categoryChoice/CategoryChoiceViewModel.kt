package com.plub.presentation.ui.main.home.categoryChoice

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryChoiceViewModel @Inject constructor(
    val categoriesGatheringUseCase: GetCategoriesGatheringUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase
) : BaseViewModel<CategoryChoiceState>(CategoryChoiceState()) {

    companion object {
        private const val FIRST_PAGE = 1
    }

    private var categoryId : Int = 0
    private var pageNumber : Int = FIRST_PAGE
    private var hasMoreCards : Boolean = true
    

    fun fetchRecommendationGatheringData(id : Int)  =
        viewModelScope.launch {
            categoryId = id
            categoriesGatheringUseCase(CategoriesGatheringRequestVo(categoryId, uiState.value.sortType.key,pageNumber)).collect{ state->
                inspectUiState(state, ::successResult)
            }
            pageNumber++
        }

    private fun fetchRecommendationGatheringData() =
        viewModelScope.launch {
            categoriesGatheringUseCase(CategoriesGatheringRequestVo(categoryId, uiState.value.sortType.key,pageNumber)).collect{ state->
                inspectUiState(state, ::successResult)
            }
            pageNumber++
        }

    private fun successResult(vo: PlubCardListVo){
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
    }

    private fun mapToCardType(list : List<PlubCardVo>) : List<PlubCardVo>{
        return list.map {
            it.copy(
                viewType = uiState.value.cardType
            )
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList = uiState.value.cardList
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || pageNumber == FIRST_PAGE) mappedList else originList + mappedList
    }

    fun clickBookmark(plubbingId : Int){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase.invoke(plubbingId)
        }
    }

    fun onClickCardType(cardType: PlubCardType){
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

    fun backPage(){
        emitEventFlow(CategoryChoiceEvent.GoToBack)
    }

    fun goToCreate(){
        emitEventFlow(CategoryChoiceEvent.GoToCreate)
    }

    fun onClickSortMenuItemType(item: DialogMenuItemType) {
        viewModelScope.launch {
            pageNumber = FIRST_PAGE
            val sortType = when(item) {
                DialogMenuItemType.SORT_TYPE_NEW -> PlubSortType.NEW
                DialogMenuItemType.SORT_TYPE_POPULAR -> PlubSortType.POPULAR
                else -> PlubSortType.POPULAR
            }
            updateSortType(sortType)
            fetchRecommendationGatheringData()
        }
    }

    fun onClickSortType(sortType: PlubSortType) {
        val menuItemType = when(sortType) {
            PlubSortType.POPULAR -> DialogMenuItemType.SORT_TYPE_POPULAR
            PlubSortType.NEW -> DialogMenuItemType.SORT_TYPE_NEW
        }

        emitEventFlow(CategoryChoiceEvent.ShowSelectSortTypeBottomSheetDialog(menuItemType))
    }

    fun clickSearch(){
        emitEventFlow(CategoryChoiceEvent.GoToSearch)
    }

    fun onScrollChanged(isBottom : Boolean, isDownScroll : Boolean){
        if(isBottom && isDownScroll && hasMoreCards) {
            updateLoadingState(true)
            fetchRecommendationGatheringData()
        }
    }

    private fun updateLoadingState(isLoading : Boolean){
        updateUiState { uiState ->
            uiState.copy(
                isLoading = isLoading
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

}