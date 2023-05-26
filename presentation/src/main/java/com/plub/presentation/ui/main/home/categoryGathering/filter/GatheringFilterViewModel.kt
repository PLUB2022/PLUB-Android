package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.CategoryError
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.FilterVo
import com.plub.domain.usecase.GetSubHobbiesUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseCategoryFilterVo
import com.plub.presentation.util.ResourceProvider
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GatheringFilterViewModel @Inject constructor(
    val getSubHobbiesUseCase: GetSubHobbiesUseCase,
    val resourceProvider: ResourceProvider
): BaseTestViewModel<GatheringFilterState>() {

    companion object{
        const val MIN_ACCOUNT_NUM = 4
    }

    private var categoryName = ""
    private val allText = resourceProvider.getString(R.string.category_choice_see_all)
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()
    private val allHobby = SubHobbyVo( 0, name = allText)


    private val gatheringDaysStateFlow: MutableStateFlow<HashSet<DaysType>> = MutableStateFlow(hashSetOf())
    private val categoryNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val subHobbiesStateFlow: MutableStateFlow<List<SubHobbyVo>> = MutableStateFlow(emptyList())
    private val seekBarProgressStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val seekBarPositionXStateFlow: MutableStateFlow<Float> = MutableStateFlow(0f)
    private val accountNumStateFlow: MutableStateFlow<Int> = MutableStateFlow(MIN_ACCOUNT_NUM)
    private val isButtonEnableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val selectedHobbiesStateFlow: MutableStateFlow<List<SelectedHobbyVo>> = MutableStateFlow(emptyList())

    override val uiState: GatheringFilterState = GatheringFilterState(
        gatheringDaysStateFlow.asStateFlow(),
        categoryNameStateFlow.asStateFlow(),
        subHobbiesStateFlow.asStateFlow(),
        seekBarProgressStateFlow.asStateFlow(),
        seekBarPositionXStateFlow.asStateFlow(),
        accountNumStateFlow.asStateFlow(),
        isButtonEnableStateFlow.asStateFlow(),
        selectedHobbiesStateFlow.asStateFlow()
    )

    fun fetchSubHobbies(categoryId : Int, categoryName : String){
        this.categoryName = categoryName
        viewModelScope.launch {
            getSubHobbiesUseCase(categoryId).collect{
                inspectUiState(it, ::handleSuccessFetchSubHobbies) { _, individual ->
                    handleCategoryError(individual as CategoryError)
                }
            }
        }
    }

    private fun handleCategoryError(categoryError: CategoryError){
        when(categoryError){
            CategoryError.Common -> TODO()
            is CategoryError.NotFoundCategory -> TODO()
        }
    }

    private fun handleSuccessFetchSubHobbies(vo : List<SubHobbyVo>){
        viewModelScope.launch {
            categoryNameStateFlow.update { categoryName }
            subHobbiesStateFlow.update {listOf(allHobby) + vo }
        }
    }

    fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
        if (isClicked) removeHobby(selectedHobbyVo) else addHobby(selectedHobbyVo)
    }

    private fun addHobby(selectedHobbyVo: SelectedHobbyVo) {
        updateAllHobby(selectedHobbyVo)
        notifySubItem(selectedHobbyVo)
        selectedList.add(selectedHobbyVo)
        updateSelectList()
        updateButtonState()
    }

    private fun updateAllHobby(selectedHobbyVo: SelectedHobbyVo){
        if(selectedHobbyVo.name == allText) {
            selectedList.forEach {
                notifySubItem(it)
            }
            selectedList.clear()
        }
        else{
            selectedList.forEach{
                if(it.name == allText){
                    selectedList.remove(it)
                    notifySubItem(it)
                }
            }
        }
    }

    private fun removeHobby(selectedHobbyVo: SelectedHobbyVo) {
        selectedList.remove(selectedHobbyVo)
        updateSelectList()
        updateButtonState()
        notifySubItem(selectedHobbyVo)
    }

    private fun updateSelectList() {
        viewModelScope.launch {
            selectedHobbiesStateFlow.update { selectedList }
        }
    }

    private fun notifySubItem(selectedHobbyVo: SelectedHobbyVo) {
        emitEventFlow(GatheringFilterEvent.NotifySubHobby(selectedHobbyVo))
    }

    fun onClickCheckBox(element: DaysType): Void? {
        viewModelScope.launch{
            gatheringDaysStateFlow.update { uiState.gatheringDays.value
                .addOrRemoveElementAfterReturnNewHashSet(element)
                .removeElementAfterReturnNewHashSet(DaysType.ALL) }
        }
        return null
    }

    fun onClickAllCheckBox(): Void? {
        viewModelScope.launch{
            gatheringDaysStateFlow.update { if (DaysType.ALL in uiState.gatheringDays.value) hashSetOf() else hashSetOf(DaysType.ALL) }
        }
        return null
    }

    val updateSeekbarProgressAndPositionX: (progress: Int, position: Float) -> Unit =
        { progress, position ->
            updateSeekbarProgress(progress)
            updateSeekbarPositionX(position)
        }

    private fun updateSeekbarProgress(progress: Int) {
        viewModelScope.launch {
            seekBarProgressStateFlow.update { progress }
            accountNumStateFlow.update { progress + MIN_ACCOUNT_NUM }
        }
    }

    private fun updateSeekbarPositionX(position: Float) {
        viewModelScope.launch {
            seekBarPositionXStateFlow.update { position }
        }
    }

    fun updateButtonState(){
        viewModelScope.launch {
            isButtonEnableStateFlow.update { selectedList.isNotEmpty() && uiState.gatheringDays.value.isNotEmpty() }
        }
    }

    fun onClickApply(){
        val isAllList = selectedList.find {
            it.name == allText
        }
        val vo = FilterVo(
            gatheringDays = uiState.gatheringDays.value,
            accountNum =  uiState.accountNum.value,
            selectedHobbies = uiState.selectedHobbies.value,
            isAll = selectedList.contains(isAllList)
        )
        emitEventFlow(GatheringFilterEvent.GoToCategoryGathering(ParseCategoryFilterVo.mapToParse(vo)))
    }

    fun onClickBack(){
        emitEventFlow(GatheringFilterEvent.GoToBack)
    }
}