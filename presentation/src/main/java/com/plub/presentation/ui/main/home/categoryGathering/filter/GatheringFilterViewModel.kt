package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.FilterVo
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.domain.usecase.GetSubHobbiesUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseCategoryFilterVo
import com.plub.presentation.ui.main.plubing.PlubingMainPageState
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GatheringFilterViewModel @Inject constructor(
    val getSubHobbiesUseCase: GetSubHobbiesUseCase
): BaseTestViewModel<GatheringFilterState>() {

    private var categoryName = ""
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()


    private val gatheringDaysStateFlow: MutableStateFlow<HashSet<DaysType>> = MutableStateFlow(hashSetOf())
    private val categoryNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val subHobbiesStateFlow: MutableStateFlow<List<SubHobbyVo>> = MutableStateFlow(emptyList())
    private val seekBarProgressStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val seekBarPositionXStateFlow: MutableStateFlow<Float> = MutableStateFlow(0f)
    private val accountNumStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
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
                inspectUiState(it, ::handleSuccessFetchSubHobbies)
            }
        }
    }

    private fun handleSuccessFetchSubHobbies(vo : List<SubHobbyVo>){
        viewModelScope.launch {
            categoryNameStateFlow.update { categoryName }
            subHobbiesStateFlow.update { vo }
        }
    }

    fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
        if (isClicked) removeHobby(selectedHobbyVo) else addHobby(selectedHobbyVo)
    }

    private fun addHobby(selectedHobbyVo: SelectedHobbyVo) {
        selectedList.add(selectedHobbyVo)
        updateSelectList()
        notifySubItem(selectedHobbyVo)
    }

    private fun removeHobby(selectedHobbyVo: SelectedHobbyVo) {
        selectedList.remove(selectedHobbyVo)
        updateSelectList()
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
            accountNumStateFlow.update { progress + 4 }
        }
    }

    private fun updateSeekbarPositionX(position: Float) {
        viewModelScope.launch {
            seekBarPositionXStateFlow.update { position }
        }
    }

    fun updateButtonState(){
        viewModelScope.launch {
            isButtonEnableStateFlow.update { uiState.selectedHobbies.value.isNotEmpty() && uiState.gatheringDays.value.isNotEmpty() }
        }
    }

    fun onClickApply(){
        val vo = FilterVo(
            gatheringDays = uiState.gatheringDays.value,
            accountNum =  uiState.accountNum.value,
            selectedHobbies = uiState.selectedHobbies.value
        )
        emitEventFlow(GatheringFilterEvent.GoToCategoryGathering(ParseCategoryFilterVo.mapToParse(vo)))
    }

    fun onClickBack(){
        emitEventFlow(GatheringFilterEvent.GoToBack)
    }
}