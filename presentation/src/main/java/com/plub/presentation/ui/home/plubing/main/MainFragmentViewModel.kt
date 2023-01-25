package com.plub.presentation.ui.home.plubing.main

import RecommendationGatheringResponseVo
import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.presentation.state.SampleHomeState
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.usecase.BrowseUseCase
import com.plub.domain.usecase.RecommendationGatheringUsecase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    val browseUseCase: BrowseUseCase,
    val recommendationGatheringUsecase: RecommendationGatheringUsecase,
) : BaseViewModel<SampleHomeState>(SampleHomeState()) {

    private val _categoryData = MutableSharedFlow<CategoryListResponseVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val categoryData: SharedFlow<CategoryListResponseVo> = _categoryData.asSharedFlow()

    private val _recommendationData = MutableSharedFlow<RecommendationGatheringResponseVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData : SharedFlow<RecommendationGatheringResponseVo> = _recommendationData.asSharedFlow()

    private val _goToCategoryChoiceFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToCategoryChoiceFragment: SharedFlow<Unit> = _goToCategoryChoiceFragment.asSharedFlow()

    fun isHaveInterest()  = viewModelScope.launch {
        browseUseCase.invoke().collect { state ->
            when(state){
                is UiState.Loading -> "로딩"
                is UiState.Success -> _categoryData.emit(state.data)
                is UiState.Error -> "에러"
            }
        }


        viewModelScope.launch {
            recommendationGatheringUsecase.invoke(RecommendationGatheringRequestVo(0)).collect{ state->
                when(state){
                    is UiState.Loading -> "로딩"
                    is UiState.Success -> _recommendationData.emit(state.data)
                    is UiState.Error -> "에러"
                }

            }
        }
    }

    fun goToCategoryChoice() {
        viewModelScope.launch {
            _goToCategoryChoiceFragment.emit(Unit)
        }
    }

//    fun isHaveInterest()  = viewModelScope.launch {
//        testPostHomeUseCase.invoke(HomePostRequestVo("testcode", false)).collect { state ->
//            when(state){
//                is UiState.Loading -> updateUiState { uiState ->
//                    uiState.copy("로딩")
//                }
//                is UiState.Success -> updateUiState { uiState ->
//                    uiState.copy("성공 + ${state.data.toString()}")
//                }
//                is UiState.Error -> updateUiState { uiState ->
//                    uiState.copy("실패 + ${state.error.toString()}")
//                }
//            }
//        }

}