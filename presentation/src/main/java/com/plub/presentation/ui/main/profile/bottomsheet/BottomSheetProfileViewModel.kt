package com.plub.presentation.ui.main.profile.bottomsheet

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.myPage.OtherProfileVo
import com.plub.domain.model.vo.todo.GetOtherTodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.GetOtherProfileUseCase
import com.plub.domain.usecase.GetOtherTodoUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetProfileViewModel @Inject constructor(
    private val getOtherProfileUseCase: GetOtherProfileUseCase,
    private val getOtherTodoUseCase: GetOtherTodoUseCase
) : BaseViewModel<BottomSheetOtherProfileState>(
    BottomSheetOtherProfileState()
) {

    companion object{
        const val FIRST_CURSOR = 0
    }
    private var profileDataList : MutableList<OtherProfileVo> = mutableListOf()
    private var isLast : Boolean = false
    private var cursorId : Int = FIRST_CURSOR

    fun fetchOtherProfile(nickName : String, accountId : Int, plubbingId : Int){
        viewModelScope.launch {
            cursorId = FIRST_CURSOR

            getOtherProfileUseCase(nickName).collect{
                inspectUiState(it, ::onSuccessGetOtherInfo)
            }
            val request = GetOtherTodoRequestVo(
                plubbingId, accountId, cursorId
            )
            getOtherTodoUseCase(request).collect{
                inspectUiState(it, ::handleGetOtherTodoSuccess)
            }
        }
    }

    private fun onSuccessGetOtherInfo(vo : MyInfoResponseVo){
        profileDataList.add(
            OtherProfileVo(
                info = vo,
                viewType = OtherProfileBottomSheetViewType.PROFILE
            )
        )
    }

    private fun handleGetOtherTodoSuccess(data: TodoTimelineListVo) {
        isLast = data.last
        data.content.forEach { vo ->
            profileDataList.add(getToDoVo(vo))
        }

        updateOtherProfileDataList(profileDataList)
    }

    private fun getToDoVo(vo : TodoTimelineVo) : OtherProfileVo{
        return OtherProfileVo(
            viewType = OtherProfileBottomSheetViewType.TODO,
            todoList = vo
        )
    }

    private fun updateOtherProfileDataList(list : List<OtherProfileVo>){
        updateUiState { uiState ->
            uiState.copy(
                dataList = list
            )
        }
    }
}