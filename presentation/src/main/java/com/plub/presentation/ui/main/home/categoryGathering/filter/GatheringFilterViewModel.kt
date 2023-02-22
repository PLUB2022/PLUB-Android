package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.usecase.GetSubHobbiesUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GatheringFilterViewModel @Inject constructor(
    val getSubHobbiesUseCase: GetSubHobbiesUseCase
): BaseViewModel<GatheringFilterState>(GatheringFilterState()) {

    private var categoryName = ""
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()
    private val selectedDayList : MutableList<String> = mutableListOf()

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
                hobbiesSelectedVo = ui.hobbiesSelectedVo.copy(selectedList)
            )
        }
    }

    private fun notifySubItem(selectedHobbyVo: SelectedHobbyVo) {
        emitEventFlow(GatheringFilterEvent.NotifySubHobby(selectedHobbyVo))
    }

    fun onClickAllDay(){
        selectedDayList.add(DaysType.ALL.eng)
        updateSelectDayList()
        emitEventFlow(GatheringFilterEvent.ClickAllDay)
    }

    fun onClickDay(daysType : DaysType){
        if(selectedDayList.contains(daysType.eng)){
            selectedDayList.remove(daysType.eng)
        }
        else{
            selectedDayList.add(daysType.eng)
        }
        updateSelectDayList()
        emitEventFlow(GatheringFilterEvent.ClickDay(daysType, selectedDayList.contains(daysType.eng)))
    }

    private fun updateSelectDayList(){
        updateUiState { uiState ->
            uiState.copy(
                dayList = selectedDayList
            )
        }
    }
}