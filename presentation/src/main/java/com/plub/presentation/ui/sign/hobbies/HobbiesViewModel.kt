package com.plub.presentation.ui.sign.hobbies

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.HobbyViewType
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.usecase.GetAllHobbiesUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HobbiesViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val getAllHobbiesUseCase: GetAllHobbiesUseCase
) : BaseViewModel<HobbiesPageState>(HobbiesPageState()) {

    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()

    fun onClickNextButton() {
        emitEventFlow(HobbiesEvent.MoveToNext(uiState.value.hobbiesSelectedVo))
    }

    fun onSaveNickname(nickname:String) {
        updateUiState { ui ->
            ui.copy(
                descriptionText = resourceProvider.getString(R.string.sign_up_hobbies_description,nickname)
            )
        }
    }

    fun onInitHobbyInfo(hobbiesVo: SignUpHobbiesVo) {
        if (uiState.value != HobbiesPageState()) return
        addAllHobby(hobbiesVo.hobbies)
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
        val addedList = getAddedHobbyFooterList(list)
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

    private fun notifyAllItem() {
        emitEventFlow(HobbiesEvent.NotifyAllHobby)
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

    private fun addAllHobby(list: List<SelectedHobbyVo>) {
        selectedList.clear()
        selectedList.addAll(list)
        updateSelectList()
        notifyAllItem()
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

    private fun getAddedHobbyFooterList(list:List<HobbyVo>):List<HobbyVo> {
        return list.toMutableList().apply {
            add(HobbyVo(viewType = HobbyViewType.LATE_PICK))
        }
    }
}