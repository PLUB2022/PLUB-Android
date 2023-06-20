package com.plub.presentation.ui.main.plubing.board

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentPlubingBoardBinding
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentDirections
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import com.plub.presentation.ui.main.plubing.board.write.BoardWriteFragment
import com.plub.presentation.util.getNavigationResult
import com.plub.presentation.util.infiniteScrolls
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingBoardFragment :
    BaseTestFragment<FragmentPlubingBoardBinding, PlubingBoardPageState, PlubingBoardViewModel>(
        FragmentPlubingBoardBinding::inflate
    ) {

    companion object {
        private const val KEY_PLUBING_ID = "KEY_PLUBING_ID"

        fun newInstance(plubindId: Int) = PlubingBoardFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_PLUBING_ID, plubindId)
            }
        }
    }

    private val plubingId: Int by lazy {
        arguments?.getInt(KEY_PLUBING_ID) ?: -1
    }

    private val boardListAdapter: PlubingBoardAdapter by lazy {
        PlubingBoardAdapter(object : PlubingBoardAdapter.Delegate {
            override fun onClickClipBoard() {
                viewModel.onClickClipBoard()
            }

            override fun onClickBoard(feedId: Int) {
                viewModel.onClickBoard(feedId)
            }

            override fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
                viewModel.onLongClickBoard(feedId, isHost, isAuthor)
            }

            override val clipBoardList: List<PlubingBoardVo>
                get() = viewModel.uiState.clipBoardList.value
        })
    }

    override val viewModel: PlubingBoardViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewBoard.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = boardListAdapter
                infiniteScrolls { viewModel.onScrollChanged() }
            }
        }
        viewModel.initPlubingId(plubingId)
        viewModel.onGetClipBoardList()
        viewModel.onGetBoardList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.boardList.collect {
                    boardListAdapter.submitList(it) {
                        viewModel.onBoardUpdated()
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as PlubingBoardEvent)
                }
            }
        }

        getNavigationResult(BoardWriteFragment.KEY_RESULT_EDIT_COMPLETE) { vo:ParsePlubingBoardVo ->
            viewModel.onCompleteBoardEdit(vo)
        }

        getNavigationResult(BoardWriteFragment.KEY_RESULT_CREATE_COMPLETE) { _:String ->
            viewModel.onCompleteBoardCreate()
        }
    }

    private fun inspectEventFlow(event: PlubingBoardEvent) {
        when (event) {
            is PlubingBoardEvent.GoToDetailBoard -> goToDetailBoard(event.feedId)
            is PlubingBoardEvent.NotifyClipBoardChanged -> notifyClipboard()
            is PlubingBoardEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.feedId, event.menuType)
            is PlubingBoardEvent.GoToEditBoard -> goToEditBoard(event.feedId)
            is PlubingBoardEvent.GoToReportBoard -> goToReport()
            is PlubingBoardEvent.GoToPinBoard -> goToPinBoard()
            is PlubingBoardEvent.ScrollToPosition -> scrollToPosition(event.position)
        }
    }

    private fun notifyClipboard() {
        boardListAdapter.notifyClipBoard()
    }

    private fun showMenuBottomSheetDialog(id:Int, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(id, it)
        }.show(parentFragmentManager, "")
    }

    private fun goToEditBoard(feedId:Int) {
        val action = PlubingMainFragmentDirections.actionPlubingMainToPlubingBoardWrite(feedId = feedId, writeType = WriteType.EDIT)
        findNavController().navigate(action)
    }

    private fun goToDetailBoard(feedId: Int) {
        val action = PlubingMainFragmentDirections.actionPlubingMainToPlubingBoardDetail(feedId)
        findNavController().navigate(action)
    }

    private fun goToReport() {

    }

    private fun goToPinBoard() {
        val action = PlubingMainFragmentDirections.actionPlubingMainToBoardPin()
        findNavController().navigate(action)
    }

    private fun scrollToPosition(position:Int) {
        binding.recyclerViewBoard.scrollToPosition(position)
    }
}