package com.plub.presentation.ui.main.plubing.todo.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentTodoDetailBinding
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.todo.TodoCheckProofDialog
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentDirections
import com.plub.presentation.ui.main.plubing.todo.adapter.TodoItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class TodoDetailFragment :
    BaseTestFragment<FragmentTodoDetailBinding, TodoDetailPageState, TodoDetailViewModel>(
        FragmentTodoDetailBinding::inflate
    ) {

    private val todoDetailArgs: TodoDetailFragmentArgs by navArgs()

    private val todoListAdapter: TodoItemAdapter by lazy {
        TodoItemAdapter(object : TodoItemAdapter.Delegate {
            override fun onClickTodoCheck(todoItemVo: TodoItemVo) {
                viewModel.onClickTodoCheck(todoItemVo)
            }

            override fun onClickTodoMenu(todoItemVo: TodoItemVo) {
                viewModel.onClickTodoMenu(todoItemVo)
            }

            override fun onClickTodoContent() {}
        })
    }

    override val viewModel: TodoDetailViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewTodo.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = todoListAdapter
            }
        }

        viewModel.initArgs(todoDetailArgs.timelineId)
        viewModel.onGetTodoDetail()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todoList.collect {
                    todoListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as TodoDetailEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: TodoDetailEvent) {
        when (event) {
            is TodoDetailEvent.GoToPlannerTodo -> goToPlanner(event.date)
            is TodoDetailEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.menuType, event.todoVo)
            is TodoDetailEvent.ShowTodoProofDialog -> showTodoProofDialog(event.parseTodoItemVo)
            is TodoDetailEvent.GoToBack -> findNavController().popBackStack()
        }
    }

    private fun showMenuBottomSheetDialog(menuType: DialogMenuType, todoItemVo: TodoItemVo) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, todoItemVo)
        }.show(parentFragmentManager, "")
    }

    private fun showTodoProofDialog(parseTodoItemVo: ParseTodoItemVo) {
        TodoCheckProofDialog.newInstance(parseTodoItemVo, object: TodoCheckProofDialog.Delegate {
            override fun onClickComplete(todoId: Int, proofFile: File) {
                viewModel.onClickProofComplete(todoId, proofFile)
            }

            override fun onClickLateProof(todoId: Int) {}
        }).show(parentFragmentManager, "")
    }

    private fun goToPlanner(date:String) {
        val action = TodoDetailFragmentDirections.actionTodoDetailToTodoPlanner(date)
        findNavController().navigate(action)
    }
}