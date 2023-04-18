package com.plub.presentation.ui.main.profile.active

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageActiveGatheringBinding
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.todo.TodoCheckProofDialog
import com.plub.presentation.ui.main.profile.active.adapter.ActiveGatheringParentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ActiveGatheringFragment :
    BaseTestFragment<FragmentMyPageActiveGatheringBinding, ActiveGatheringPageState, ActiveGatheringViewModel>(
        FragmentMyPageActiveGatheringBinding::inflate
    ) {

    private val activeGatheringFragmentArgs : ActiveGatheringFragmentArgs by navArgs()

    private val activeGatheringParentAdapter: ActiveGatheringParentAdapter by lazy {
        ActiveGatheringParentAdapter(object : ActiveGatheringParentAdapter.ActiveGatheringDelegate{
            override fun onClickBoard(feedId: Int) {
                viewModel.onClickBoard(feedId)
            }

            override fun onClickTimeline(timelineId: Int) {
                viewModel.onClickTimeline(timelineId)
            }

            override fun onClickTodoCheck(timelineId: Int, vo: TodoItemVo) {
                viewModel.onClickTodoCheck(timelineId, vo)
            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                viewModel.onClickTodoMenu(vo)
            }

            override fun onClickTodoLike(timelineId: Int) {
                viewModel.onClickTodoLike(timelineId)
            }

            override fun onClickEmptyBoard() {
                viewModel.goToWriteBoard()
            }

            override fun onClickEmptyTodo() {
                viewModel.goToTodoPlanner("")
            }

            override fun onClickAllMyBoard() {
                viewModel.goToAllMyBoard()
            }

            override fun onClickAllMyTodo() {
                viewModel.goToAllMyTodo()
            }
        })
    }

    override val viewModel: ActiveGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewMyPageContent.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = activeGatheringParentAdapter
            }

        }
        viewModel.setPlubIdAndStateType(activeGatheringFragmentArgs.plubbingId, activeGatheringFragmentArgs.stateType)
        viewModel.setView()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.detailList.collect {
                    activeGatheringParentAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ActiveGatheringEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: ActiveGatheringEvent) {
        when (event) {
            is ActiveGatheringEvent.GoToDetailBoard -> goToDetailBoard(event.feedId)
            is ActiveGatheringEvent.GoToPlubbingMain -> goToPlubbingMain(event.plubbingId)
            is ActiveGatheringEvent.GoToBack -> findNavController().popBackStack()
            is ActiveGatheringEvent.GoToDetailTodo -> goToDetailTodo(event.timelineId)
            is ActiveGatheringEvent.ShowTodoProofDialog -> showTodoProofDialog(event.timelineId, event.parseTodoItemVo)
            is ActiveGatheringEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.todoTimelineVo, event.menuType)
            is ActiveGatheringEvent.GoToPlannerTodo -> goToPlanner(event.date)
            is ActiveGatheringEvent.GoToWriteBoard -> goToWriteBoard()
            is ActiveGatheringEvent.GoToAllMyBoard -> goToAllMyBoard()
            is ActiveGatheringEvent.GoToAllMyTodo -> goToAllMyToDo()
        }
    }

    private fun goToDetailBoard(feedId: Int) {
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubingBoardDetail(feedId)
        findNavController().navigate(action)
    }

    private fun goToPlubbingMain(plubbingId : Int){
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubbingMain(plubbingId)
        findNavController().navigate(action)
    }

    private fun goToDetailTodo(timelineId: Int) {
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubingTodoDetail(timelineId)
        findNavController().navigate(action)
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
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubingTodoPlanner(date)
        findNavController().navigate(action)
    }

    private fun goToWriteBoard(){
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubingBoardWrite(writeType = PlubingBoardWriteType.CREATE)
        findNavController().navigate(action)
    }

    private fun goToAllMyBoard(){
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToAllMyPost()
        findNavController().navigate(action)
    }

    private fun goToAllMyToDo(){
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToAllMyTodo()
        findNavController().navigate(action)
    }
}