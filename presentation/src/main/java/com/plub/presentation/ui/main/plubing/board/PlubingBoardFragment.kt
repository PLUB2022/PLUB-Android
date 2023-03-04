package com.plub.presentation.ui.main.plubing.board

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingBoardBinding
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentDirections
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import com.plub.presentation.ui.main.plubing.board.write.BoardWriteFragment
import com.plub.presentation.util.getNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingBoardFragment :
    BaseFragment<FragmentPlubingBoardBinding, PlubingBoardPageState, PlubingBoardViewModel>(
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

            override fun onClickNormalBoard(feedId: Int) {
                viewModel.onClickNormalBoard(feedId)
            }

            override fun onLongClickNormalBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
                viewModel.onLongClickNormalBoard(feedId, isHost, isAuthor)
            }

            override val clipBoardList: List<PlubingBoardVo>
                get() = viewModel.uiState.value.clipBoardList
        })
    }

    override val viewModel: PlubingBoardViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewBoard.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = boardListAdapter

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
        viewModel.initPlubingId(plubingId)
        viewModel.onFetchBoardList()
        viewModel.onFetchClipBoardList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    boardListAdapter.submitList(it.boardList) {
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

        getNavigationResult(BoardWriteFragment.KEY_RESULT_CREATE_COMPLETE) { _:Unit ->
            viewModel.onCompleteBoardCreate()
        }
    }

    private fun inspectEventFlow(event: PlubingBoardEvent) {
        when (event) {
            is PlubingBoardEvent.GoToDetailBoard -> {
                val action = PlubingMainFragmentDirections.actionPlubingMainToPlubingBoardDetail(event.feedId)
                findNavController().navigate(action)
            }
            is PlubingBoardEvent.NotifyClipBoardChanged -> {
                boardListAdapter.notifyClipBoard()
            }

            is PlubingBoardEvent.ShowMenuBottomSheetDialog -> {
                showMenuBottomSheetDialog(event.feedId, event.menuType)
            }

            is PlubingBoardEvent.GoToEditBoard -> goToEditBoard(event.feedId)
            is PlubingBoardEvent.GoToReportBoard -> Unit
            is PlubingBoardEvent.ScrollToPosition -> binding.recyclerViewBoard.scrollToPosition(event.position)
        }
    }

    private fun showMenuBottomSheetDialog(id:Int, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(id, it)
        }.show(parentFragmentManager, "")
    }

    private fun goToEditBoard(feedId:Int) {
        val action = PlubingMainFragmentDirections.actionPlubingMainToPlubingBoardWrite(feedId = feedId, writeType = PlubingBoardWriteType.EDIT)
        findNavController().navigate(action)
    }
}