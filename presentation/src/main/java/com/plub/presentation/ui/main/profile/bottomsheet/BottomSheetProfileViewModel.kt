package com.plub.presentation.ui.main.profile.bottomsheet

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.myPage.OtherProfileVo
import com.plub.domain.model.vo.todo.GetOtherTodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.GetOtherProfileUseCase
import com.plub.domain.usecase.GetOtherTodoUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.profile.active.myTodo.MyPageAllMyTodoEvent
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

    fun onClickTodoMenu(vo: TodoTimelineVo) {
        val menuType = when {
            vo.isAuthor -> DialogMenuType.TODO_LIST_AUTHOR_TYPE
            else -> DialogMenuType.TODO_LIST_COMMON_TYPE
        }
        emitEventFlow(BottomSheetProfileEvent.ShowMenuBottomSheetDialog(vo, menuType))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoTimelineVo: TodoTimelineVo) {
        when (item) {
            DialogMenuItemType.CAMERA_IMAGE -> TODO()
            DialogMenuItemType.ALBUM_IMAGE -> TODO()
            DialogMenuItemType.DEFAULT_IMAGE -> TODO()
            DialogMenuItemType.SORT_TYPE_NEW -> TODO()
            DialogMenuItemType.SORT_TYPE_POPULAR -> TODO()
            DialogMenuItemType.BOARD_EDIT -> TODO()
            DialogMenuItemType.BOARD_DELETE -> TODO()
            DialogMenuItemType.BOARD_REPORT -> TODO()
            DialogMenuItemType.BOARD_FIX_CLIP -> TODO()
            DialogMenuItemType.BOARD_RELEASE_CLIP -> TODO()
            DialogMenuItemType.BOARD_FIX_OR_RELEASE_CLIP -> TODO()
            DialogMenuItemType.BOARD_COMMENT_DELETE -> TODO()
            DialogMenuItemType.BOARD_COMMENT_EDIT -> TODO()
            DialogMenuItemType.BOARD_COMMENT_REPORT -> TODO()
            DialogMenuItemType.SCHEDULE_EDIT -> TODO()
            DialogMenuItemType.SCHEDULE_DELETE -> TODO()
            DialogMenuItemType.TODO_REPORT -> TODO()
            DialogMenuItemType.TODO_PLANNER -> TODO()
            DialogMenuItemType.TODO_PROOF -> TODO()
            DialogMenuItemType.TODO_DELETE -> TODO()
            DialogMenuItemType.TODO_EDIT -> TODO()
            DialogMenuItemType.ARCHIVE_EDIT -> TODO()
            DialogMenuItemType.ARCHIVE_DELETE -> TODO()
            DialogMenuItemType.ARCHIVE_REPORT -> TODO()
        }
    }

    fun onClickClose(){
        emitEventFlow(BottomSheetProfileEvent.CloseButtonClick)
    }
}