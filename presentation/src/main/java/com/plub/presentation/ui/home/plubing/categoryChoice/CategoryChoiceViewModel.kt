package com.plub.presentation.ui.home.plubing.categoryChoice

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.GatheringShapeType
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.successOrNull
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

    private val _recommendationData =
        MutableSharedFlow<RecommendationGatheringResponseVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData: SharedFlow<RecommendationGatheringResponseVo> =
        _recommendationData.asSharedFlow()

    fun fetchRecommendationGatheringData(categoryId : Int, pageNumber : Int)  =
        viewModelScope.launch {
            categoriesGatheringUseCase.invoke(CategoriesGatheringRequestVo(categoryId, pageNumber)).collect{ state->
                state.successOrNull()?.let { _recommendationData.emit(it) }
            }
        }

    fun clickBookmark(plubbingId : Int){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase.invoke(plubbingId)
        }
    }

    fun gridBtnClick(){
        updateUiState { ui ->
            ui.copy(
                listOrGrid = GatheringShapeType.GRID
            )
        }
    }

    fun listBtnClick(){
        updateUiState { ui ->
            ui.copy(
                listOrGrid = GatheringShapeType.LIST
            )
        }
    }

    fun backMainPage(){
        emitEventFlow(CategoryChoiceEvent.GoToMain)
    }

}