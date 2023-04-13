package com.plub.presentation.ui.main.home.categoryGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.*
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringBodyRequestVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringParamsVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.FilterVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.domain.usecase.PostBookmarkPlubRecruitUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseCategoryFilterVo
import com.plub.presentation.ui.main.home.categoryGathering.filter.GatheringFilterState
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryGatheringViewModel @Inject constructor(
    val categoriesGatheringUseCase: GetCategoriesGatheringUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val resourceProvider: ResourceProvider
) : BaseTestViewModel<CategoryGatheringState>() {

    companion object {
        private const val FIRST_CURSOR = 0
    }

    private val categoryNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val cardListStateFlow: MutableStateFlow<List<PlubCardVo>> = MutableStateFlow(emptyList())
    private val cardTypeStateFlow: MutableStateFlow<PlubCardType> = MutableStateFlow(PlubCardType.LIST)
    private val isEmptyViewVisibleStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val sortTypeStateFlow: MutableStateFlow<PlubSortType> = MutableStateFlow(PlubSortType.POPULAR)
    private val sortTypeNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: CategoryGatheringState = CategoryGatheringState(
        categoryNameStateFlow.asStateFlow(),
        cardListStateFlow.asStateFlow(),
        cardTypeStateFlow.asStateFlow(),
        isEmptyViewVisibleStateFlow.asStateFlow(),
        sortTypeStateFlow.asStateFlow(),
        sortTypeNameStateFlow.asStateFlow(),
    )

    private var categoryId: Int = 0
    private var cursorId: Int = FIRST_CURSOR
    private var isLast : Boolean = true
    private var isNetworkCall: Boolean = false


    fun updateCategoryNameWithId(name : String, id : Int){
        viewModelScope.launch {
            categoryNameStateFlow.update { name }
        }
        categoryId = id
    }

    fun fetchRecommendationGatheringData(body : ParseCategoryFilterVo) =
        viewModelScope.launch {
            cursorId = FIRST_CURSOR
            isNetworkCall = true
            clearCardList()
            val paramsVo = CategoriesGatheringParamsVo(categoryId, uiState.sortType.value.key, cursorId)
            val bodyVo = getBodyVo(ParseCategoryFilterVo.mapToDomain(body))
            categoriesGatheringUseCase(
                CategoriesGatheringRequestVo(paramsVo, bodyVo)
            ).collect { state ->
                inspectUiState(state, ::successResult)
            }
        }


    fun updateSortTypeName(sortType : PlubSortType){
        val sortTypeRes = when (sortType) {
            PlubSortType.POPULAR -> R.string.word_sort_type_popular
            PlubSortType.NEW -> R.string.word_sort_type_new
        }

        viewModelScope.launch {
            sortTypeNameStateFlow.update { resourceProvider.getString(sortTypeRes) }
        }
    }

    private fun getBodyVo(body : FilterVo) : CategoriesGatheringBodyRequestVo{
        val days = if(body.gatheringDays.isEmpty() || body.gatheringDays.contains(DaysType.ALL)) null else body.gatheringDays.map { it.eng }
        val subCategoryId = if(body.selectedHobbies.isEmpty()) null else getMergeSelectedHobbyList(body.selectedHobbies)
        val accountNum = if(body.accountNum == 0) null else body.accountNum

        return CategoriesGatheringBodyRequestVo(
            days = days,
            subCategoryId = subCategoryId,
            accountNum = accountNum
        )
    }

    private fun getMergeSelectedHobbyList(list : List<SelectedHobbyVo>) : List<Int>{
        val subCategoryIdList = mutableListOf<Int>()
        for(content in list){
            subCategoryIdList.add(content.subId)
        }

        return subCategoryIdList
    }

    private fun fetchRecommendationGatheringData() =
        viewModelScope.launch {
            isNetworkCall = true
            val paramsVo = CategoriesGatheringParamsVo(categoryId, uiState.sortType.value.key, cursorId)
            val bodyVo = CategoriesGatheringBodyRequestVo()
            categoriesGatheringUseCase(
                CategoriesGatheringRequestVo(paramsVo, bodyVo)
            ).collect { state ->
                inspectUiState(state, ::successResult)
            }
        }

    private fun updateCardList(list : List<PlubCardVo>){
        viewModelScope.launch{
            cardListStateFlow.update { list }
            isEmptyViewVisibleStateFlow.update { list.isEmpty() }
        }
    }

    private fun clearCardList(){
        viewModelScope.launch{
            cardListStateFlow.update { emptyList() }
        }
    }

    private fun successResult(vo: PlubCardListVo) {
        isNetworkCall = false
        isLast = vo.last
        val mappedList = mapToCardType(vo.content)
        val mergedList = getMergeList(mappedList)
        updateCardList(mergedList)
    }


    private fun mapToCardType(list: List<PlubCardVo>): List<PlubCardVo> {
        return list.map {
            it.copy(
                viewType = uiState.cardType.value
            )
        }
    }

    private fun getMergeList(list: List<PlubCardVo>): List<PlubCardVo> {
        val originList =  uiState.cardList.value
        val mappedList = mapToCardType(list)
        return if (originList.isEmpty() || cursorId == FIRST_CURSOR)  mappedList else originList + mappedList
    }

    fun scrollTop() {
        if (cursorId == FIRST_CURSOR) {
            emitEventFlow(CategoryGatheringEvent.ScrollTop)
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
        val list = uiState.cardList.value
        val newList = list.map {
            val bookmark = if (it.id == vo.id) vo.isBookmarked else it.isBookmarked
            it.copy(
                isBookmarked = bookmark
            )
        }
        updateSearchList(newList)
    }

    private fun updateSearchList(list: List<PlubCardVo>) {
        viewModelScope.launch {
            cardListStateFlow.update { list }
        }
    }

    fun onClickCardType(cardType: PlubCardType) {
        cursorId = FIRST_CURSOR
        viewModelScope.launch {
            cardTypeStateFlow.update { cardType }
            fetchRecommendationGatheringData()
        }
    }

    fun backPage() {
        emitEventFlow(CategoryGatheringEvent.GoToBack)
    }

    fun goToCreate() {
        emitEventFlow(CategoryGatheringEvent.GoToCreate)
    }

    fun onClickSortMenuItemType(item: DialogMenuItemType) {
        viewModelScope.launch {
            cursorId = FIRST_CURSOR
            val sortType = when (item) {
                DialogMenuItemType.SORT_TYPE_NEW -> PlubSortType.NEW
                DialogMenuItemType.SORT_TYPE_POPULAR -> PlubSortType.POPULAR
                else -> PlubSortType.POPULAR
            }
            updateSortType(sortType)
            fetchRecommendationGatheringData()
        }
    }

    private fun updateSortType(sortType: PlubSortType) {
        viewModelScope.launch{
            sortTypeStateFlow.update { sortType }
        }
    }

    fun onClickSortType(sortType: PlubSortType) {
        val menuItemType = when (sortType) {
            PlubSortType.POPULAR -> DialogMenuItemType.SORT_TYPE_POPULAR
            PlubSortType.NEW -> DialogMenuItemType.SORT_TYPE_NEW
        }

        emitEventFlow(CategoryGatheringEvent.ShowSelectSortTypeBottomSheetDialog(menuItemType))
    }

    fun goToDetailRecruitment(id: Int, isHost: Boolean) {
        if (isHost) {
            emitEventFlow(CategoryGatheringEvent.GoToHostRecruit(id))
        } else {
            emitEventFlow(CategoryGatheringEvent.GoToRecruit(id))
        }
    }

    fun clickSearch() {
        emitEventFlow(CategoryGatheringEvent.GoToSearch)
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (!isNetworkCall && isBottom && isDownScroll && !isLast) {
            cursorUpdate()
            fetchRecommendationGatheringData()
        }
    }

    private fun cursorUpdate() {
        cursorId = if (cardListStateFlow.value.isEmpty()) FIRST_CURSOR
        else cardListStateFlow.value.lastOrNull()?.id ?: FIRST_CURSOR
    }

    fun goToFilter() {
        emitEventFlow(CategoryGatheringEvent.GoToFilter)
    }

    fun fetchRecommendationAllGatheringData(){
        fetchRecommendationGatheringData(ParseCategoryFilterVo())
    }
}