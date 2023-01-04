package com.plub.presentation.ui.sign.hobbies

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.HobbyViewType
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.domain.usecase.GetAllHobbiesUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.HobbiesPageState
import com.plub.presentation.state.MoreInfoPageState
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HobbiesViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val getAllHobbiesUseCase: GetAllHobbiesUseCase
) : BaseViewModel<HobbiesPageState>(HobbiesPageState()) {

    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()

    private val _moveToNextPage = MutableSharedFlow<SignUpHobbiesVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val moveToNextPage: SharedFlow<SignUpHobbiesVo> = _moveToNextPage.asSharedFlow()
    private val _notifySubHobby = MutableSharedFlow<SelectedHobbyVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val notifySubHobby: SharedFlow<SelectedHobbyVo> = _notifySubHobby.asSharedFlow()
    private val _notifyAllHobby = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val notifyAllHobby: SharedFlow<Unit> = _notifyAllHobby.asSharedFlow()

    fun onClickNextButton() {
        viewModelScope.launch {
            _moveToNextPage.emit(uiState.value.hobbiesSelectedVo)
        }
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
        viewModelScope.launch {
            _moveToNextPage.emit(SignUpHobbiesVo(emptyList()))
        }
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
        viewModelScope.launch {
            _notifySubHobby.emit(selectedHobbyVo)
        }
    }

    private fun notifyAllItem() {
        viewModelScope.launch {
            _notifyAllHobby.emit(Unit)
        }
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