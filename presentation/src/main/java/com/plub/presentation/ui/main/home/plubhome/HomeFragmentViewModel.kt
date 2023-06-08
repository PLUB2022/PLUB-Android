package com.plub.presentation.ui.main.home.plubhome

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.HomeViewType
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.HomePlubListVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getMyInterestUseCase: GetMyInterestUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val getRecommendationGatheringUsecase: GetRecommendationGatheringUsecase
) : BaseTestViewModel<HomePageState>() {

    companion object {
        private const val FIRST_CURSOR = 0
    }

    private val homePlubListStateFlow: MutableStateFlow<List<HomePlubListVo>> = MutableStateFlow(emptyList())
    private var cursorId: Int = FIRST_CURSOR
    private var categoryList : List<HomePlubListVo> = emptyList()
    private var noHobbyList : List<HomePlubListVo> = emptyList()
    private var gatheringList : MutableList<HomePlubListVo> = mutableListOf()
    private var isLast: Boolean = true
    private var isNetworkCall: Boolean = false

    override val uiState: HomePageState = HomePageState(
        homePlubListStateFlow.asStateFlow()
    )

    fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        emitEventFlow(HomeEvent.GoToCategoryGathering(categoryId, categoryName))
    }

    fun goToRecruitment(plubbingId: Int) {
        emitEventFlow(HomeEvent.GoToRecruitment(plubbingId))
    }

    fun goToRegisterInterest() {
        emitEventFlow(HomeEvent.GoToRegisterInterest)
    }

    fun clickBookmark(plubbingId: Int) {
        viewModelScope.launch {
            postBookmarkPlubRecruitUseCase(plubbingId).collect {
                inspectUiState(it, ::postBookmarkSuccess)
            }
        }
    }

    private fun postBookmarkSuccess(vo: PlubBookmarkResponseVo) {
        val list = uiState.homePlubList.value
        val newList = list.map {
            val bookmark =
                if (it.recommendGathering.id == vo.id && it.viewType == HomeViewType.RECOMMEND_GATHERING_VIEW) vo.isBookmarked else it.recommendGathering.isBookmarked
            it.copy(
                recommendGathering = it.recommendGathering.copy(isBookmarked = bookmark)
            )
        }

        updatePlubGatheringList(newList)
    }

    private fun updatePlubGatheringList(list: List<HomePlubListVo>) {
        viewModelScope.launch {
            homePlubListStateFlow.update { list }
        }
    }

    fun fetchHomePageData() {
        viewModelScope.launch {
            isNetworkCall = true
            cursorId = FIRST_CURSOR
            gatheringList.clear()
            val jobCategories: Job = launch {
                getCategoriesUseCase(Unit).collect { state ->
                    inspectUiState(state, ::handleGetCategoriesSuccess)
                }
            }

            val jobMyInterest: Job = launch {
                getMyInterestUseCase(Unit).collect { state ->
                    inspectUiState(state, ::handleGetMyInterestSuccess)
                }
            }

            val jobRecommend: Job = launch {
                getRecommendationGatheringUsecase(cursorId).collect { state ->
                    inspectUiState(state, ::handleGetRecommendGatheringSuccess)
                }
            }

            joinAll(jobCategories, jobMyInterest, jobRecommend)
            isNetworkCall = false
            updatePlubGatheringList(categoryList + noHobbyList + gatheringList)
        }
    }

    private fun handleGetCategoriesSuccess(data: CategoryListDataResponseVo) {
        categoryList = getMergeCategoryList(data)
    }

    private fun getMergeCategoryList(data: CategoryListDataResponseVo): List<HomePlubListVo> {
        return getTitleViewList() + getCategoryViewList(data.categories)
    }

    private fun getTitleViewList(): List<HomePlubListVo> {
        return listOf(
            HomePlubListVo(viewType = HomeViewType.TITLE_VIEW)
        )
    }

    private fun getCategoryViewList(list: List<CategoriesDataResponseVo>): List<HomePlubListVo> {
        return listOf(
            HomePlubListVo(
                viewType = HomeViewType.CATEGORY_VIEW,
                categoryList = list
            ),
            HomePlubListVo(
                viewType = HomeViewType.RECOMMEND_TITLE_VIEW
            )
        )
    }

    private fun handleGetMyInterestSuccess(data: RegisterInterestResponseVo) {
        if(data.subCategories.isEmpty()){
            noHobbyList = listOf(
                HomePlubListVo(
                viewType = HomeViewType.REGISTER_HOBBIES_VIEW
            )
            )
        }
    }

    private fun handleGetRecommendGatheringSuccess(data: PlubCardListVo) {
        isLast = data.last
        data.content.forEach { vo ->
            gatheringList.add(getGatheringsVo(vo))
        }
        if(!isLast) gatheringList.add(HomePlubListVo(viewType = HomeViewType.LOADING))
    }

    private fun getGatheringsVo(data: PlubCardVo): HomePlubListVo {
        return HomePlubListVo(
            viewType = HomeViewType.RECOMMEND_GATHERING_VIEW,
            recommendGathering = data
        )
    }

    fun onScrollChanged() {
        if (!isNetworkCall && !isLast) {
            isNetworkCall = true
            gatheringList.removeLast()
            cursorUpdate()
            fetchRecommendationGatheringData()
        }
    }

    private fun fetchRecommendationGatheringData() {
        isNetworkCall = true
        viewModelScope.launch {
            getRecommendationGatheringUsecase(cursorId)
                .collect { state ->
                    inspectUiState(state, ::handleGetNextRecommendGatheringSuccess, needShowLoading = false)
                }
        }
    }

    private fun handleGetNextRecommendGatheringSuccess(data: PlubCardListVo) {
        isNetworkCall = false
        handleGetRecommendGatheringSuccess(data)
        updatePlubGatheringList(categoryList + noHobbyList + gatheringList)
    }

    private fun cursorUpdate() {
        cursorId = if (homePlubListStateFlow.value.isEmpty()) FIRST_CURSOR
        else homePlubListStateFlow.value.lastOrNull { it.viewType == HomeViewType.RECOMMEND_GATHERING_VIEW }?.recommendGathering?.id ?: FIRST_CURSOR
    }

    fun goToSearch() {
        emitEventFlow(HomeEvent.GoToSearch)
    }

    fun goToBookmark() {
        emitEventFlow(HomeEvent.GoToBookMark)
    }

    fun goToCreateGathering(){
        emitEventFlow(HomeEvent.GoToCreateGathering)
    }
}