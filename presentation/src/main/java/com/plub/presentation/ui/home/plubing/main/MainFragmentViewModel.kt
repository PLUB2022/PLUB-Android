package com.plub.presentation.ui.home.plubing.main

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.BrowseUseCase
import com.plub.domain.usecase.RecommendationGatheringUsecase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.MainPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    val browseUseCase: BrowseUseCase,
    val recommendationGatheringUsecase: RecommendationGatheringUsecase
) : BaseViewModel<MainPageState>(MainPageState()) {

    private val _categoryData = MutableSharedFlow<CategoryListResponseVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val categoryData: SharedFlow<CategoryListResponseVo> = _categoryData.asSharedFlow()

    private val _recommendationData = MutableSharedFlow<RecommendationGatheringResponseVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData : SharedFlow<RecommendationGatheringResponseVo> = _recommendationData.asSharedFlow()

    private val _goToCategoryChoiceFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToCategoryChoiceFragment: SharedFlow<Unit> = _goToCategoryChoiceFragment.asSharedFlow()

    fun initMainPage()  = viewModelScope.launch {
        browseUseCase.invoke().collect { state ->
            inspectUiState(state, ::handleGetCategoriesSuccess)
        }

        viewModelScope.launch {
            recommendationGatheringUsecase.invoke(RecommendationGatheringRequestVo(0)).collect{ state->
                state.successOrNull()?.let { _recommendationData.emit(it) }
            }
        }
    }

    private fun handleGetCategoriesSuccess(data: CategoryListResponseVo) {
        updateUiState { ui ->
            ui.copy(
                categoryVo = data
            )
        }
    }



    fun goToCategoryChoice() {
        viewModelScope.launch {
            _goToCategoryChoiceFragment.emit(Unit)
        }
    }


}