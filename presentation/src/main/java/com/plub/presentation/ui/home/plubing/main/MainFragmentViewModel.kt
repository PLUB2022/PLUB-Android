package com.plub.presentation.ui.home.plubing.main

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.GetHobbiesUseCase
import com.plub.domain.usecase.GetRecommendationGatheringUsecase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    val getHobbiesUseCase: GetHobbiesUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val getRecommendationGatheringUsecase: GetRecommendationGatheringUsecase
) : BaseViewModel<PageState.Default>(PageState.Default) {

    private val _categoryData = MutableSharedFlow<CategoryListResponseVo>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val categoryData: SharedFlow<CategoryListResponseVo> = _categoryData.asSharedFlow()

    private val _recommendationData =
        MutableSharedFlow<PlubCardListVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData: SharedFlow<PlubCardListVo> =
        _recommendationData.asSharedFlow()


    fun fetchMainPageData() =
        viewModelScope.launch {
            getHobbiesUseCase(Unit).collect { state ->
                state.successOrNull()?.let { _categoryData.emit(it) }
                //inspectUiState(state, ::handleGetCategoriesSuccess)
            }

            getRecommendationGatheringUsecase(RecommendationGatheringRequestVo(0))
                .collect { state ->
                    state.successOrNull()?.let { _recommendationData.emit(it) }
                }
        }

    fun clickBookmark(plubbingId : Int){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase(plubbingId)
        }
    }

}