package com.plub.presentation.ui.main.plubing.todo

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentPlubingTodoBinding
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.todo.TodoCheckProofDialog
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentDirections
import com.plub.presentation.ui.main.plubing.todo.adapter.PlubingTodoAdapter
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.infiniteScrolls
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class PlubingTodoFragment : BaseTestFragment<FragmentPlubingTodoBinding, PlubingTodoPageState, PlubingTodoViewModel>(
    FragmentPlubingTodoBinding::inflate
) {

    companion object {
        private const val KEY_PLUBING_ID = "KEY_PLUBING_ID"

        fun newInstance(plubindId: Int) = PlubingTodoFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_PLUBING_ID, plubindId)
            }
        }
    }

    private val plubingId: Int by lazy {
        arguments?.getInt(KEY_PLUBING_ID) ?: -1
    }

    private val todoListAdapter: PlubingTodoAdapter by lazy {
        PlubingTodoAdapter(object : PlubingTodoAdapter.Delegate {

            override fun onClickTimeline(timelineId: Int) {
                viewModel.onClickTimeline(timelineId)
            }

            override fun onClickTodoChecked(timelineId:Int, vo: TodoItemVo) {
                viewModel.onClickTodoCheck(timelineId, vo)
            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                viewModel.onClickTodoMenu(vo)
            }

            override fun onClickTodoLike(timelineId: Int) {
                viewModel.onClickTodoLike(timelineId)
            }
        })
    }

    override val viewModel: PlubingTodoViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewTodo.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = todoListAdapter
                infiniteScrolls { viewModel.onScrollChanged() }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onGetTodoList()
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
                    inspectEventFlow(it as PlubingTodoEvent)
                }
            }
        }

        viewModel.initPlubingId(plubingId)
        viewModel.onGetTodoList()
    }

    private fun inspectEventFlow(event: PlubingTodoEvent) {
        when (event) {
            is PlubingTodoEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.todoTimelineVo, event.menuType)
            is PlubingTodoEvent.ShowTodoProofDialog -> showTodoProofDialog(event.timelineId, event.parseTodoItemVo)
            is PlubingTodoEvent.GoToPlannerTodo -> goToPlanner(event.date)
            is PlubingTodoEvent.GoToDetailTodo -> goToDetail(event.timelineId)
        }
    }

    private fun showMenuBottomSheetDialog(todoTimelineVo: TodoTimelineVo, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, todoTimelineVo)
        }.show(parentFragmentManager, "")
    }

    private fun showTodoProofDialog(timelineId: Int, parseTodoItemVo: ParseTodoItemVo) {
        TodoCheckProofDialog.newInstance(parseTodoItemVo, object: TodoCheckProofDialog.Delegate {
            override fun onClickComplete(todoId: Int, proofFile: File) {
                viewModel.onClickProofComplete(timelineId, todoId, proofFile)
            }

            override fun onClickLateProof(todoId: Int) {}
        }).show(parentFragmentManager, "")
    }

    private fun goToPlanner(date:String) {
        val action = PlubingMainFragmentDirections.actionPlubingMainToPlubingTodoPlanner(date)
        findNavController().navigate(action)
    }

    private fun goToDetail(timelineId: Int) {
        val action = PlubingMainFragmentDirections.actionPlubingMainToPlubingTodoDetail(timelineId)
        findNavController().navigate(action)
    }
}