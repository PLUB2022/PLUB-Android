package com.plub.presentation.ui.main.home.plubhome

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubHomeRecommendViewType
import com.plub.domain.model.enums.HomeCategoryPlubType
import com.plub.domain.model.enums.HomeCategoryViewType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.home.categoryChoice.CategoryGatheringViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getMyInterestUseCase: GetMyInterestUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val getRecommendationGatheringUsecase: GetRecommendationGatheringUsecase
) : BaseViewModel<HomePageState>(HomePageState()) {

    companion object {
        private const val FIRST_PAGE = 1
    }

    private var pageNumber: Int = FIRST_PAGE
    private var hasMoreCards: Boolean = true
    private var isNetworkCall : Boolean = false

    fun fetchHomePageData() =
        viewModelScope.launch {
            getCategoriesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetCategoriesSuccess)
            }

            getMyInterestUseCase(Unit).collect{ state ->
                inspectUiState(state, ::handleGetMyInterestSuccess)
            }

            getRecommendationGatheringUsecase(pageNumber)
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
        }

    private fun fetchRecommendationGatheringData(){
        isNetworkCall = true
        viewModelScope.launch {
            getRecommendationGatheringUsecase(pageNumber)
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
        }
    }

    private fun handleGetCategoriesSuccess(data : CategoryListDataResponseVo){
        isNetworkCall = false
        val mergeList = getMergeList(data)
        updateUiState { ui->
            ui.copy(
                categories = mergeList,
                categoryOrPlub = HomeCategoryPlubType.CATEGORY
            )
        }
    }

    private fun getMergeList(data : CategoryListDataResponseVo) : List<CategoryListDataResponseVo>{
        val firstViewList = mutableListOf<CategoryListDataResponseVo>()
        firstViewList.add(CategoryListDataResponseVo(viewType = HomeCategoryViewType.FIRST_VIEW))
        return firstViewList + data
    }

    private fun handleGetMyInterestSuccess(data : RegisterInterestResponseVo){
        updateUiState { ui->
            ui.copy(
                hasInterest = data.subCategories.isNotEmpty()
            )
        }
    }

    private fun handleGetRecommendGatheringSuccess(data : PlubCardListVo){
        hasMoreCards = (pageNumber != data.totalPages)
        updateUiState { ui->
            ui.copy(
                plubCardList = getList(data),
                categoryOrPlub = HomeCategoryPlubType.RECOMMEND_GATHERING,
                isLoading = hasMoreCards
            )
        }
        pageNumber++
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

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (!isNetworkCall && isBottom && isDownScroll && hasMoreCards) {
            fetchRecommendationGatheringData()
        }
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