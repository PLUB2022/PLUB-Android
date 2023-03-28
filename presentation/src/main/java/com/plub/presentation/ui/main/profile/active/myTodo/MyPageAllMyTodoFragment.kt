package com.plub.presentation.ui.main.profile.active.myTodo

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageAllMyTodoBinding
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.todo.TodoCheckProofDialog
import com.plub.presentation.ui.main.profile.active.adapter.MyPageTodoTimeLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MyPageAllMyTodoFragment :
    BaseTestFragment<FragmentMyPageAllMyTodoBinding, MyPageAllMyTodoState, MyPageAllMyTodoViewModel>(
        FragmentMyPageAllMyTodoBinding::inflate
    ) {

    override val viewModel: MyPageAllMyTodoViewModel by viewModels()

    private val myPageTodoTimeLineAdapter: MyPageTodoTimeLineAdapter by lazy {
        MyPageTodoTimeLineAdapter(object : MyPageTodoTimeLineAdapter.MyPageTodoDelegate {
            override fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo) {
                viewModel.onClickTodoCheck(timelineId, vo)
            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                viewModel.onClickTodoMenu(vo)
            }

            override fun onClickTodoLike(timelineId: Int) {
                viewModel.onClickTodoLike(timelineId)
            }

            override fun onClickTimeline(timelineId: Int) {
                viewModel.onClickTimeline(timelineId)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewTodoList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myPageTodoTimeLineAdapter

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisiblePosition =
                            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                        val isDownScroll = dy > 0
                        viewModel.onScrollChanged(isBottom, isDownScroll)
                    }
                })
            }
        }

        viewModel.getMyToDoList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todoList.collect {
                    myPageTodoTimeLineAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as MyPageAllMyTodoEvent)
                }
            }
        }
    }

    private fun inspectEvent(event : MyPageAllMyTodoEvent){
        when(event){
            is MyPageAllMyTodoEvent.GoToBack -> findNavController().popBackStack()
            is MyPageAllMyTodoEvent.ShowTodoProofDialog -> showTodoProofDialog(event.timelineId, event.parseTodoItemVo)
            is MyPageAllMyTodoEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.todoTimelineVo, event.menuType)
            is MyPageAllMyTodoEvent.GoToDetailTodo -> goToDetail(event.timelineId)
            is MyPageAllMyTodoEvent.GoToPlannerTodo -> goToPlanner(event.date)
        }
    }

    private fun showTodoProofDialog(timelineId: Int, parseTodoItemVo: ParseTodoItemVo) {
        TodoCheckProofDialog.newInstance(parseTodoItemVo, object: TodoCheckProofDialog.Delegate {
            override fun onClickComplete(todoId: Int, proofFile: File) {
                viewModel.onClickProofComplete(timelineId, todoId, proofFile)
            }

            override fun onClickLateProof(todoId: Int) {}
        }).show(parentFragmentManager, "")
    }

    private fun showMenuBottomSheetDialog(todoTimelineVo: TodoTimelineVo, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, todoTimelineVo)
        }.show(parentFragmentManager, "")
    }

    private fun goToPlanner(date:String) {
        val action = MyPageAllMyTodoFragmentDirections.actionMyPageAllMyTodoToPlubingTodoPlanner(date)
        findNavController().navigate(action)
    }

    private fun goToDetail(timelineId: Int) {
        val action = MyPageAllMyTodoFragmentDirections.actionMyPageAllMyTodoToPlubingTodoDetail(timelineId)
        findNavController().navigate(action)
    }
}