package com.plub.presentation.ui.main.home.plubhome

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.HomeViewType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.HomePlubListVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseViewModel
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
    private var isNetworkCall: Boolean = false
    private var hasInterest: Boolean = false

    fun fetchHomePageData() =
        viewModelScope.launch {
            pageNumber = FIRST_PAGE
            getCategoriesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetCategoriesSuccess)
            }

            getMyInterestUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetMyInterestSuccess)
            }

            getRecommendationGatheringUsecase(pageNumber)
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
        }

    private fun fetchRecommendationGatheringData() {
        isNetworkCall = true
        viewModelScope.launch {
            getRecommendationGatheringUsecase(pageNumber)
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
        }
    }

    private fun handleGetCategoriesSuccess(data: CategoryListDataResponseVo) {
        val categoryList = getMergeCategoryList(data)
        updateUiState { uiState ->
            uiState.copy(
                homePlubList = categoryList,
                isVisible = false
            )
        }
    }

    private fun getMergeCategoryList(data: CategoryListDataResponseVo): List<HomePlubListVo> {
        val mergedCategoryList = mutableListOf<HomePlubListVo>()
        mergedCategoryList.add(HomePlubListVo(viewType = HomeViewType.TITLE_VIEW))
        mergedCategoryList.add(
            HomePlubListVo(
                viewType = HomeViewType.CATEGORY_VIEW,
                categoryList = data.categories
            )
        )
        return mergedCategoryList
    }

    private fun handleGetMyInterestSuccess(data: RegisterInterestResponseVo) {
        hasInterest = data.subCategories.isNotEmpty()
    }

    private fun handleGetRecommendGatheringSuccess(data: PlubCardListVo) {
        hasMoreCards = (pageNumber != data.totalPages)
        updateUiState { ui ->
            ui.copy(
                homePlubList = addList(data),
                isVisible = true
            )
        }
        isNetworkCall = false
        pageNumber++
    }

    private fun addList(it: PlubCardListVo): List<HomePlubListVo> {
        val originList = uiState.value.homePlubList
        val mergedList = mutableListOf<HomePlubListVo>()
        if (pageNumber == FIRST_PAGE) {
            mergedList.add(
                HomePlubListVo(
                    viewType = HomeViewType.RECOMMEND_TITLE_VIEW,
                )
            )
            if (!hasInterest) {
                mergedList.add(
                    HomePlubListVo(
                        viewType = HomeViewType.REGISTER_HOBBIES_VIEW
                    )
                )
            }
        }
        for (i in 0 until it.content.size) {
            mergedList.add(
                HomePlubListVo(
                    viewType = HomeViewType.RECOMMEND_GATHERING_VIEW,
                    recommendGathering = it.content[i]
                )
            )
        }
        return originList + mergedList
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (!isNetworkCall && isBottom && isDownScroll && hasMoreCards) {
            fetchRecommendationGatheringData()
        }
    }

    fun clickBookmark(plubbingId: Int) {
        viewModelScope.launch {
            postBookmarkPlubRecruitUseCase(plubbingId).collect {
                inspectUiState(it, ::postBookmarkSuccess)
            }
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = uiState.value.homePlubList
        val newList = list.map {
            val bookmark =
                if (it.recommendGathering.id == vo.id) vo.isBookmarked else it.recommendGathering.isBookmarked
            it.copy(
                recommendGathering = it.recommendGathering.copy(isBookmarked = bookmark)
            )
        }

        updatePlubGatheringList(newList)
    }

    private fun updatePlubGatheringList(list: List<HomePlubListVo>) {
        updateUiState { uiState ->
            uiState.copy(
                homePlubList = list
            )
        }
    }

    fun goToSearch() {
        emitEventFlow(PlubbingHomeEvent.GoToSearch)
    }

    fun goToBookmark() {
        emitEventFlow(PlubbingHomeEvent.GoToBookMark)
    }

    fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        emitEventFlow(PlubbingHomeEvent.GoToCategoryGathering(categoryId, categoryName))
    }

}