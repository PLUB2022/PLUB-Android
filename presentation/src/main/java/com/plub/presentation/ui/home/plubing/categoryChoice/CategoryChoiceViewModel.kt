package com.plub.presentation.ui.home.plubing.categoryChoice


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.categoriesgatheringvo.CategoriesGatheringRequestVo
import com.plub.presentation.state.SampleHomeState
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.CategoryChoiceState
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryChoiceViewModel @Inject constructor(
    val categoriesGatheringUseCase: GetCategoriesGatheringUseCase
) : BaseViewModel<CategoryChoiceState>(CategoryChoiceState()) {


    private val _recommendationData =
        MutableSharedFlow<RecommendationGatheringResponseVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData: SharedFlow<RecommendationGatheringResponseVo> =
        _recommendationData.asSharedFlow()

    fun fetchRecommendationGatheringData(categoryId : Int)  =
        viewModelScope.launch {
            categoriesGatheringUseCase.invoke(CategoriesGatheringRequestVo(categoryId, 0)).collect{ state->
                state.successOrNull()?.let { _recommendationData.emit(it) }
            }
    }

    fun gridBtnClick(){
        //TODO 그리드로 변경
        updateUiState { ui ->
            ui.copy(
                listOrGrid = true
            )
        }
    }

    fun listBtnClick(){
        //TODO 리스트로 변경
        updateUiState { ui ->
            ui.copy(
                listOrGrid = false
            )
        }
    }

}