package com.plub.presentation.ui.main.home.plubhome

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubHomeRecommendViewType
import com.plub.domain.model.enums.HomeCategoryPlubType
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.usecase.GetHobbiesUseCase
import com.plub.domain.usecase.GetMyInterestUseCase
import com.plub.domain.usecase.GetRecommendationGatheringUsecase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    val getHobbiesUseCase: GetHobbiesUseCase,
    val getMyInterestUseCase: GetMyInterestUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val getRecommendationGatheringUsecase: GetRecommendationGatheringUsecase
) : BaseViewModel<HomePageState>(HomePageState()) {

    fun fetchMainPageData() =
        viewModelScope.launch {
            getHobbiesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetCategoriesSuccess)
            }

            getMyInterestUseCase(Unit).collect{ state ->
                inspectUiState(state, ::handleGetMyInterestSuccess)
            }

            getRecommendationGatheringUsecase(0)
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
        }

    private fun handleGetCategoriesSuccess(data : CategoryListResponseVo){
        updateUiState { ui->
            ui.copy(
                categoryVo = data.data,
                categoryOrPlub = HomeCategoryPlubType.CATEGORY
            )
        }
    }

    private fun handleGetMyInterestSuccess(data : RegisterInterestResponseVo){
        updateUiState { ui->
            ui.copy(
                hasInterest = data.subCategories.isNotEmpty()
            )
        }
    }

    private fun handleGetRecommendGatheringSuccess(data : PlubCardListVo){
        updateUiState { ui->
            ui.copy(
                plubCardList = getList(data),
                categoryOrPlub = HomeCategoryPlubType.RECOMMEND_GATHERING
            )
        }
    }

    private fun getList(it: PlubCardListVo): List<RecommendationGatheringResponseVo> {
        val list: MutableList<RecommendationGatheringResponseVo> = mutableListOf()
        list.add(0, RecommendationGatheringResponseVo(
            viewType = PlubHomeRecommendViewType.FIRST_VIEW
        ))
        if (!uiState.value.hasInterest) {
            list.add(
                1, RecommendationGatheringResponseVo(
                    viewType = PlubHomeRecommendViewType.REGISTER_HOBBIES_VIEW,
                )
            )
        }
        list.add(RecommendationGatheringResponseVo(
            content = it.content
        ))
        return list
    }

    fun clickBookmark(plubbingId : Int){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase(plubbingId)
        }
    }

    fun goToSearch(){
        emitEventFlow(PlubbingMainEvent.GoToSearch)
    }

    fun goToBookmark(){
        emitEventFlow(PlubbingMainEvent.GoToBookMark)
    }

}