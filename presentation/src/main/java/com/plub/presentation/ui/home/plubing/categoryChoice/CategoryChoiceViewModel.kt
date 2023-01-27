package com.plub.presentation.ui.home.plubing.categoryChoice

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.CategoryChoiceEvent
import com.plub.presentation.state.CategoryChoiceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
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

    private val _recommendationData =
        MutableSharedFlow<RecommendationGatheringResponseVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData: SharedFlow<RecommendationGatheringResponseVo> =
        _recommendationData.asSharedFlow()

    fun fetchRecommendationGatheringData(id : Int, pages : Int)  =
        viewModelScope.launch {
            categoryId = id
            pageNumber = pages
            categoriesGatheringUseCase.invoke(CategoriesGatheringRequestVo(categoryId, pageNumber)).collect{ state->
                inspectUiState(state, ::successResult)
            }
        }

    private fun fetchRecommendationGatheringData()  =
        viewModelScope.launch {
            categoriesGatheringUseCase.invoke(CategoriesGatheringRequestVo(categoryId, pageNumber)).collect{ state->
                inspectUiState(state, ::successResult)
            }
        }

    private fun successResult(vo: PlubCardListVo){
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateUiState { ui ->
            ui.copy(
                cardList = mergedList,
                isEmptyViewVisible = mergedList.isEmpty()
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

    fun backMainPage(){
        emitEventFlow(CategoryChoiceEvent.GoToMain)
    }

}