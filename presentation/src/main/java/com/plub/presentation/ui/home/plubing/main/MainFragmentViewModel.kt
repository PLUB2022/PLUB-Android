package com.plub.presentation.ui.home.plubing.main

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MainPageCategoryPlubType
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.GetHobbiesUseCase
import com.plub.domain.usecase.GetRecommendationGatheringUsecase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.PlubbingMainEvent
import com.plub.presentation.state.MainPageState
import com.plub.presentation.state.PageState
import com.plub.presentation.util.PlubLogger
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
) : BaseViewModel<MainPageState>(MainPageState()) {

    fun fetchMainPageData() =
        viewModelScope.launch {
            getHobbiesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetCategoriesSuccess)
            }

            getRecommendationGatheringUsecase(RecommendationGatheringRequestVo(0))
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
        }

    private fun handleGetCategoriesSuccess(data : CategoryListResponseVo){
        updateUiState { ui->
            ui.copy(
                categoryVo = data.data,
                categoryOrPlub = MainPageCategoryPlubType.CATEGORY
            )
        }
    }

    private fun handleGetRecommendGatheringSuccess(data : PlubCardListVo){
        updateUiState { ui->
            ui.copy(
                plubCardList = data,
                categoryOrPlub = MainPageCategoryPlubType.PLUB
            )
        }
    }

    fun clickBookmark(plubbingId : Int){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase(plubbingId)
        }
    }

    fun goToSearch(){
        PlubLogger.logD("검색 클릭")
        emitEventFlow(PlubbingMainEvent.GoToSearch)
    }

    fun goToBookmark(){
        PlubLogger.logD("북마크 클릭")
        emitEventFlow(PlubbingMainEvent.GoToBookMark)
    }

}