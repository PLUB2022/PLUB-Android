package com.plub.presentation.ui.main.home.registhobbies

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.usecase.GetAllHobbiesUseCase
import com.plub.domain.usecase.PostRegisterHobbiesUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.sign.hobbies.HobbiesEvent
import com.plub.presentation.ui.sign.hobbies.HobbiesPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterHobbiesViewModel @Inject constructor(
    val getAllHobbiesUseCase: GetAllHobbiesUseCase,
    val postRegisterHobbiesUseCase: PostRegisterHobbiesUseCase
) : BaseViewModel<HobbiesPageState>(HobbiesPageState()) {
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()

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

    private fun updateHobbies(hobbies:List<HobbyVo>) {
        updateUiState { ui ->
            ui.copy(
                hobbiesVo = hobbies
            )
        }
    }

    fun onClickExpand(hobbyId: Int) {
        val hobbiesList = uiState.value.hobbiesVo.map {
            val expanded = if(it.id == hobbyId) !it.isExpand else it.isExpand
            it.copy(isExpand = expanded)
        }
        updateHobbies(hobbiesList)
    }

    fun onClickNextButton() {
        emitEventFlow(HobbiesEvent.MoveToNext(uiState.value.hobbiesSelectedVo))
    }


    fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
        if (isClicked) removeHobby(selectedHobbyVo) else addHobby(selectedHobbyVo)
    }

    private fun removeHobby(selectedHobbyVo: SelectedHobbyVo) {
        selectedList.remove(selectedHobbyVo)
        updateSelectList()
        notifySubItem(selectedHobbyVo)
    }

    fun onClickLatePick() {
        emitEventFlow(HobbiesEvent.MoveToNext(SignUpHobbiesVo(emptyList())))
    }


    private fun notifySubItem(selectedHobbyVo: SelectedHobbyVo) {
        emitEventFlow(HobbiesEvent.NotifySubHobby(selectedHobbyVo))
    }

    private fun addHobby(selectedHobbyVo: SelectedHobbyVo) {
        selectedList.add(selectedHobbyVo)
        updateSelectList()
        notifySubItem(selectedHobbyVo)
    }


    private fun updateSelectList() {
        updateUiState { ui ->
            ui.copy(
                isNextButtonEnable = isNextButtonEnable(),
                hobbiesSelectedVo = ui.hobbiesSelectedVo.copy(selectedList)
            )
        }
    }

    private fun isNextButtonEnable(): Boolean {
        return selectedList.isNotEmpty()
    }


    fun registerInterest(){
        viewModelScope.launch {
            postRegisterHobbiesUseCase.invoke(getMergeList()).collect{ state ->
                inspectUiState(state, ::handleRegisterHobbiesSuccess)
            }
        }
    }

    private fun getMergeList() : List<Int>{
        val mergedList = mutableListOf<Int>()
        selectedList.forEach {
            mergedList.add(it.subId)
        }
        return mergedList
    }

    private fun handleRegisterHobbiesSuccess(vo : RegisterInterestResponseVo){
        backPage()
    }

    fun backPage(){
        emitEventFlow(RegisterHobbiesEvent.BackPage)
    }
}