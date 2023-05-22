package com.plub.presentation.ui.main.profile.other

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentOtherProfileBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.profile.active.adapter.MyPageTodoTimeLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtherProfileFragment :
    BaseTestFragment<FragmentOtherProfileBinding, OtherProfileState, OtherProfileViewModel>(
        FragmentOtherProfileBinding::inflate
    ) {

    private val otherProfileFragmentArgs : OtherProfileFragmentArgs by navArgs()

    private val todoTimeLineAdapter: MyPageTodoTimeLineAdapter by lazy {
        MyPageTodoTimeLineAdapter(object : MyPageTodoTimeLineAdapter.MyPageTodoDelegate {
            override fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo) {
                //
            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                viewModel.onClickTodoMenu(vo)
            }

            override fun onClickTodoLike(timelineId: Int) {
                viewModel.onClickTodoLike(timelineId)
            }

            override fun onClickTimeline(timelineId: Int) {
                //
            }
        })
    }

    override val viewModel: OtherProfileViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewTodo.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = todoTimeLineAdapter
            }
        }

        viewModel.fetchOtherProfile(otherProfileFragmentArgs.nickname)
        viewModel.fetchOtherTodo(otherProfileFragmentArgs.plubbingId, otherProfileFragmentArgs.accountId)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todoList.collect{
                    todoTimeLineAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as OtherProfileEvent)
                }
            }
        }
    }

    private fun inspectEvent(event : OtherProfileEvent){
        when(event){
            is OtherProfileEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.todoTimelineVo, event.menuType)
            is OtherProfileEvent.CloseButtonClick -> findNavController().popBackStack()
        }
    }

    private fun showMenuBottomSheetDialog(todoTimelineVo: TodoTimelineVo, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, todoTimelineVo)
        }.show(parentFragmentManager, "")
    }
}