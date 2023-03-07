package com.plub.presentation.ui.main.plubing.board.pin

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentBoardPinBinding
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import com.plub.presentation.ui.main.plubing.board.write.BoardWriteFragment
import com.plub.presentation.util.getNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoardPinFragment :
    BaseFragment<FragmentBoardPinBinding, BoardPinPageState, BoardPinViewModel>(
        FragmentBoardPinBinding::inflate
    ) {

    private val pinListAdapter: PlubingBoardAdapter by lazy {
        PlubingBoardAdapter(object : PlubingBoardAdapter.Delegate {
            override fun onClickClipBoard() {}
            override fun onClickBoard(feedId: Int) {
                viewModel.onClickBoard(feedId)
            }

            override fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
                viewModel.onLongClickBoard(feedId, isHost, isAuthor)
            }
            override val clipBoardList: List<PlubingBoardVo> = emptyList()
        })
    }

    override val viewModel: BoardPinViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewBoard.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = pinListAdapter
            }
        }
        viewModel.onFetchPinList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    pinListAdapter.submitList(it.pinList) {
                        viewModel.onPinUpdated()
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as BoardPinEvent)
                }
            }
        }

        getNavigationResult(BoardWriteFragment.KEY_RESULT_EDIT_COMPLETE) { vo:ParsePlubingBoardVo ->
            viewModel.onCompleteBoardEdit(vo)
        }
    }

    private fun inspectEventFlow(event: BoardPinEvent) {
        when (event) {
            is BoardPinEvent.GoToDetailBoard -> goToDetailBoard(event.feedId)
            is BoardPinEvent.GoToEditBoard -> goToEditBoard(event.feedId)
            is BoardPinEvent.GoToReportBoard -> goToReport()
            is BoardPinEvent.ScrollToPosition -> scrollToPosition(event.position)
            is BoardPinEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.feedId,event.menuType)
        }
    }

    private fun showMenuBottomSheetDialog(id:Int, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(id, it)
        }.show(parentFragmentManager, "")
    }

    private fun goToEditBoard(feedId:Int) {
        val action = BoardPinFragmentDirections.actionBoardPinToPlubingBoardWrite(feedId = feedId, writeType = PlubingBoardWriteType.EDIT)
        findNavController().navigate(action)
    }

    private fun goToDetailBoard(feedId:Int) {
        val action = BoardPinFragmentDirections.actionBoardPinToBoardDetail(feedId)
        findNavController().navigate(action)
    }

    private fun goToReport() {

    }

    private fun scrollToPosition(position:Int) {
        binding.recyclerViewBoard.scrollToPosition(position)
    }
}