package com.plub.presentation.ui.createGathering.selectPlubCategory

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.usecase.GetAllHobbiesUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringSelectPlubCategoryViewModel @Inject constructor(
    private val getAllHobbiesUseCase: GetAllHobbiesUseCase
) :
    BaseViewModel<CreateGatheringSelectPlubCategoryPageState>(
        CreateGatheringSelectPlubCategoryPageState()
    ) {
    private val maxCategoryCount = 5

    fun initUiState(savedUiState: PageState) {
        if (uiState.value != CreateGatheringSelectPlubCategoryPageState())
            return

        if (savedUiState is CreateGatheringSelectPlubCategoryPageState) {
            updateUiState { uiState ->
                uiState.copy(
                    categoriesVo = savedUiState.categoriesVo,
                    categoriesSelectedVo = savedUiState.categoriesSelectedVo
                )
            }
        }
    }

    fun onClickExpand(hobbyId: Int) {
        val hobbiesList = uiState.value.categoriesVo.map {
            val expanded = if (it.id == hobbyId) !it.isExpand else it.isExpand
            it.copy(isExpand = expanded)
        }
        updateHobbies(hobbiesList)
    }

    fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
        if (isClicked) removeHobby(selectedHobbyVo) else addHobby(selectedHobbyVo)
    }

    fun fetchHobbiesData() {
        viewModelScope.launch {
            getAllHobbiesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetAllHobbiesSuccess)
            }
        }
    }

    private fun handleGetAllHobbiesSuccess(list: List<HobbyVo>) {
        updateHobbies(list)
    }

    private fun updateSelectList(selectedList: List<SelectedHobbyVo>) {
        updateUiState { ui ->
            ui.copy(
                categoriesSelectedVo = ui.categoriesSelectedVo.copy(selectedList)
            )
        }
    }

    private fun notifySubItem(selectedHobbyVo: SelectedHobbyVo) {
        emitEventFlow(CreateGatheringSelectPlubCategoryEvent.NotifySubHobby(selectedHobbyVo))
    }

    private fun notifyAllItem() {
        emitEventFlow(CreateGatheringSelectPlubCategoryEvent.NotifyAllHobby)
    }

    private fun addHobby(selectedHobbyVo: SelectedHobbyVo) {
        if (uiState.value.categoriesSelectedVo.hobbies.size == maxCategoryCount) return

        updateSelectList(uiState.value.categoriesSelectedVo.hobbies.plus(selectedHobbyVo))
        notifySubItem(selectedHobbyVo)
    }

    private fun removeHobby(selectedHobbyVo: SelectedHobbyVo) {

        updateSelectList(uiState.value.categoriesSelectedVo.hobbies.minus(selectedHobbyVo))
        notifySubItem(selectedHobbyVo)
    }

    private fun addAllHobby(list: List<SelectedHobbyVo>) {
        updateSelectList(list)
        notifyAllItem()
    }

    private fun updateHobbies(categories: List<HobbyVo>) {
        updateUiState { uiState ->
            uiState.copy(
                categoriesVo = categories
            )
        }
    }
}