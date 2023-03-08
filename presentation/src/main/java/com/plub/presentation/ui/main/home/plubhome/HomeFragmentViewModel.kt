package com.plub.presentation.ui.main.home.plubhome

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.HomeViewType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.HomePlubListVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
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
            val jobCategories : Job = launch {
                getCategoriesUseCase(Unit).collect { state ->
                    inspectUiState(state, ::handleGetCategoriesSuccess)
                }
            }

            val jobMyInterest : Job = launch {
                getMyInterestUseCase(Unit).collect { state ->
                    inspectUiState(state, ::handleGetMyInterestSuccess)
                }
            }
            joinAll(jobCategories, jobMyInterest)
            getRecommendationGatheringUsecase(pageNumber)
                .collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
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
                homePlubList = if(hasMoreCards) addRecommendGatheringList(data) else addRecommendGatheringList(data),
                isVisible = true
            )
        }
        isNetworkCall = false
        pageNumber++
    }

    private fun addRecommendGatheringList(it: PlubCardListVo): List<HomePlubListVo> {
        val originList = uiState.value.homePlubList
        val mergedList = mutableListOf<HomePlubListVo>()
        addFirstViewList(mergedList)
        it.content.forEach { vo ->
            mergedList.add(getGatheringsVo(vo))
        }

        return originList + mergedList
    }

    private fun addFirstViewList(list : MutableList<HomePlubListVo>){
        if (pageNumber == FIRST_PAGE) {
            list.add(getRecommendTitleVo())
            if (!hasInterest) {
                list.add(getRegisterHobbiesVo())
            }
        }
    }

    private fun getRecommendTitleVo() : HomePlubListVo{
        return HomePlubListVo(viewType = HomeViewType.RECOMMEND_TITLE_VIEW)
    }

    private fun getRegisterHobbiesVo() : HomePlubListVo{
        return HomePlubListVo(viewType = HomeViewType.REGISTER_HOBBIES_VIEW)
    }

    private fun getGatheringsVo(data : PlubCardVo) : HomePlubListVo{
        return HomePlubListVo(
            viewType = HomeViewType.RECOMMEND_GATHERING_VIEW,
            recommendGathering = data
        )
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (!isNetworkCall && isBottom && isDownScroll && hasMoreCards) {
            fetchRecommendationGatheringData()
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
        emitEventFlow(HomeEvent.GoToSearch)
    }

    fun goToBookmark() {
        emitEventFlow(HomeEvent.GoToBookMark)
    }

    fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        emitEventFlow(HomeEvent.GoToCategoryGathering(categoryId, categoryName))
    }

    fun goToRecruitment(plubbingId: Int){
        emitEventFlow(HomeEvent.GoToRecruitment(plubbingId))
    }

    fun goToRegisterInterest(){
        emitEventFlow(HomeEvent.GoToRegisterInterest)
    }

}