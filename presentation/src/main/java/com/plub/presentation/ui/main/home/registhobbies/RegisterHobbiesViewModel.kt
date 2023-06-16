package com.plub.presentation.ui.main.home.registhobbies

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.CategoryError
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.usecase.GetAllHobbiesUseCase
import com.plub.domain.usecase.GetMyInterestUseCase
import com.plub.domain.usecase.PostRegisterHobbiesUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.sign.hobbies.HobbiesEvent
import com.plub.presentation.ui.sign.hobbies.HobbiesPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterHobbiesViewModel @Inject constructor(
    val getAllHobbiesUseCase: GetAllHobbiesUseCase,
    val getMyInterestUseCase: GetMyInterestUseCase,
    private val postRegisterHobbiesUseCase: PostRegisterHobbiesUseCase
) : BaseViewModel<HobbiesPageState>(HobbiesPageState()) {
    private val selectedList: MutableList<SelectedHobbyVo> = mutableListOf()

    fun fetchHobbiesData() {
        viewModelScope.launch {
            getAllHobbiesUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleGetAllHobbiesSuccess, individualErrorCallback = { _, individual ->
                    handleCategoryError(individual as CategoryError)
                })
            }
        }
    }

    private fun handleCategoryError(categoryError: CategoryError){
        when(categoryError){
            is CategoryError.NotFoundCategory -> TODO()
            else -> TODO()
        }
    }

    private fun handleGetAllHobbiesSuccess(list: List<HobbyVo>) {
        updateHobbies(list)
        fetchMyHobbies(list)
    }

    private fun updateHobbies(hobbies:List<HobbyVo>) {
        updateUiState { ui ->
            ui.copy(
                hobbiesVo = hobbies
            )
        }
    }

    private fun fetchMyHobbies(list: List<HobbyVo>){
        viewModelScope.launch {
            getMyInterestUseCase(Unit).collect{
                inspectUiState(it, { state -> handleGetMyInterestSuccess(state, list)}, individualErrorCallback = { _, individual ->
                    handleCategoryError(individual as CategoryError)
                })
            }
        }
    }

    private fun handleGetMyInterestSuccess(vo : RegisterInterestResponseVo, list: List<HobbyVo>){
        val subHobbyList = mutableListOf<SubHobbyVo>()
        vo.subCategories.forEach { subId ->
            list.forEach { hobbyVo ->
                hobbyVo.subHobbies.find { it.id == subId }?.let { subHobbyList.add(it) }
            }
        }

        subHobbyList.forEach {
            addHobby(SelectedHobbyVo(
                parentId = it.parentHobbyId,
                subId = it.id,
                name = it.name
            ))
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