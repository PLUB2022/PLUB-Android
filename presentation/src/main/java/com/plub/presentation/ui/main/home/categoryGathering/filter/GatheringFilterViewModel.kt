package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.usecase.GetSubHobbiesUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.addOrRemoveElementAfterReturnNewHashSet
import com.plub.presentation.util.removeElementAfterReturnNewHashSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GatheringFilterViewModel @Inject constructor(
    val getSubHobbiesUseCase: GetSubHobbiesUseCase
): BaseViewModel<GatheringFilterState>(GatheringFilterState()) {

    private var categoryName = ""
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()

    fun fetchSubHobbies(categoryId : Int, categoryName : String){
        this.categoryName = categoryName
        viewModelScope.launch {
            getSubHobbiesUseCase(categoryId).collect{
                inspectUiState(it, ::handleSuccessFetchSubHobbies)
            }
        }
    }

    private fun handleSuccessFetchSubHobbies(vo : List<SubHobbyVo>){
        updateUiState { uiState ->
            uiState.copy(
                categoryName = categoryName,
                subHobbies = vo
            )
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
        updateUiState { ui ->
            ui.copy(
                selectedHobbies = selectedList,
                isButtonEnable = selectedList.isNotEmpty() && ui.gatheringDays.isNotEmpty()
            )
        }
    }

    private fun notifySubItem(selectedHobbyVo: SelectedHobbyVo) {
        emitEventFlow(GatheringFilterEvent.NotifySubHobby(selectedHobbyVo))
    }

    fun onClickCheckBox(element: DaysType): Void? {
        updateUiState { uiState ->
            uiState.copy(
                gatheringDays = uiState.gatheringDays
                    .addOrRemoveElementAfterReturnNewHashSet(element)
                    .removeElementAfterReturnNewHashSet(DaysType.ALL),
                isButtonEnable = uiState.selectedHobbies.isNotEmpty() && uiState.gatheringDays.isNotEmpty()
            )
        }
        return null
    }

    fun onClickAllCheckBox(): Void? {
        updateUiState { uiState ->
            uiState.copy(
                gatheringDays = if (DaysType.ALL in uiState.gatheringDays) hashSetOf() else hashSetOf(DaysType.ALL)
            )
        }
        return null
    }

    val updateSeekbarProgressAndPositionX: (progress: Int, position: Float) -> Unit =
        { progress, position ->
            updateSeekbarProgress(progress)
            updateSeekbarPositionX(position)
        }

    private fun updateSeekbarProgress(progress: Int) {
        updateUiState { uiState ->
            uiState.copy(
                seekBarProgress = progress,
                accountNum = progress + 4
            )
        }
    }

    private fun updateSeekbarPositionX(position: Float) {
        updateUiState { uiState ->
            uiState.copy(
                seekBarPositionX = position
            )
        }
    }

    fun onClickApply(){
        emitEventFlow(GatheringFilterEvent.GoToCategoryGathering(uiState.value))
    }

    fun onClickBack(){
        emitEventFlow(GatheringFilterEvent.GoToBack)
    }
}