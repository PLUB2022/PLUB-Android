package com.plub.presentation.ui.home.plubing.registinterests

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.HobbyViewType
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.usecase.GetAllHobbiesUseCase
import com.plub.domain.usecase.RegistInterestUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.HobbiesEvent
import com.plub.presentation.state.HobbiesPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterInterestViewModel @Inject constructor(
    val getAllHobbiesUseCase: GetAllHobbiesUseCase,
    val registInterestUseCase: RegistInterestUseCase
) : BaseViewModel<HobbiesPageState>(HobbiesPageState()) {
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()

    private val _emitChoice = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val emitChoice: SharedFlow<Unit> = _emitChoice.asSharedFlow()


    fun onClickNextButton() {
        emitEventFlow(HobbiesEvent.MoveToNext(uiState.value.hobbiesSelectedVo))
    }

    fun onClickExpand(hobbyId: Int) {
        val hobbiesList = uiState.value.hobbiesVo.map {
            val expanded = if(it.id == hobbyId) !it.isExpand else it.isExpand
            it.copy(isExpand = expanded)
        }
        updateHobbies(hobbiesList)
    }

    fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
        if (isClicked) removeHobby(selectedHobbyVo) else addHobby(selectedHobbyVo)
    }

    fun onClickLatePick() {
        emitEventFlow(HobbiesEvent.MoveToNext(SignUpHobbiesVo(emptyList())))
    }

    fun fetchHobbiesData() {
        viewModelScope.launch {
            getAllHobbiesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetAllHobbiesSuccess)
            }
        }
    }

    private fun handleGetAllHobbiesSuccess(list: List<HobbyVo>) {
        val addedList = getAddedHobbyFirstList(list)
        updateHobbies(addedList)
    }

    private fun updateSelectList() {
        updateUiState { ui ->
            ui.copy(
                isNextButtonEnable = isNextButtonEnable(),
                hobbiesSelectedVo = ui.hobbiesSelectedVo.copy(selectedList)
            )
        }
    }

    private fun notifySubItem(selectedHobbyVo: SelectedHobbyVo) {
        emitEventFlow(HobbiesEvent.NotifySubHobby(selectedHobbyVo))
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

    private fun updateHobbies(hobbies:List<HobbyVo>) {
        updateUiState { ui ->
            ui.copy(
                hobbiesVo = hobbies
            )
        }
    }

    private fun isNextButtonEnable(): Boolean {
        return selectedList.isNotEmpty()
    }

    private fun getAddedHobbyFirstList(list:List<HobbyVo>):List<HobbyVo> {
        return list.toMutableList().apply {
            add(0, HobbyVo(viewType = HobbyViewType.FIRST_TOPIC))
        }
    }

    fun registerInterest(selectedList: List<SelectedHobbyVo>){
        var list : MutableList<Int> = emptyList<Int>().toMutableList()
        for (data in selectedList){
            list.add(data.subId)
        }

        viewModelScope.launch {
            registInterestUseCase.invoke(list)
        }
    }

    fun completeChoice(){
        viewModelScope.launch {
            _emitChoice.emit(Unit)
        }
    }

}