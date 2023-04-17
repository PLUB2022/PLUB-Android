package com.plub.presentation.ui.main.profile.active.myPost

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageAllMyPostBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.infiniteScrolls
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageAllMyPostFragment :
    BaseTestFragment<FragmentMyPageAllMyPostBinding, MyPageAllMyPostState, MyPageAllMyPostViewModel>(
        FragmentMyPageAllMyPostBinding::inflate
    ) {

    private val boardListAdapter: PlubingBoardAdapter by lazy {
        PlubingBoardAdapter(object : PlubingBoardAdapter.Delegate {
            override fun onClickClipBoard() {
            }

            override fun onClickBoard(feedId: Int) {
                viewModel.onClickBoard(feedId)
            }

            override fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
                viewModel.onLongClickBoard(feedId, isHost, isAuthor)
            }

            override val clipBoardList: List<PlubingBoardVo>
                get() = emptyList()
        })
    }

    override val viewModel: MyPageAllMyPostViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewPostList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = boardListAdapter
                infiniteScrolls { viewModel.onScrollChanged() }
            }
        }
        viewModel.setPlubId(PlubingInfo.info.plubingId)
        viewModel.onFetchBoardList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.boardList.collect {
                    boardListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as MyPageAllMyPostEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: MyPageAllMyPostEvent) {
        when (event) {
            is MyPageAllMyPostEvent.GoToDetailBoard -> goToDetailBoard(event.feedId)
            is MyPageAllMyPostEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.feedId, event.menuType)
            is MyPageAllMyPostEvent.GoToEditBoard -> goToEditBoard(event.feedId)
            is MyPageAllMyPostEvent.GoToReportBoard -> goToReport()
            is MyPageAllMyPostEvent.GoToPinBoard -> goToPinBoard()
            is MyPageAllMyPostEvent.GoToBack -> findNavController().popBackStack()
        }
    }

    private fun goToDetailBoard(feedId: Int) {
        val action = MyPageAllMyPostFragmentDirections.actionMyPageAllMyPostToPlubingBoardDetail(feedId)
        findNavController().navigate(action)
    }

    private fun showMenuBottomSheetDialog(id:Int, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(id, it)
        }.show(parentFragmentManager, "")
    }

    private fun goToEditBoard(feedId:Int) {
        val action = MyPageAllMyPostFragmentDirections.actionMyPageAllMyPostToPlubbingBoardWrite(feedId = feedId, writeType = PlubingBoardWriteType.EDIT)
        findNavController().navigate(action)
    }

    private fun goToReport() {

    }

    private fun goToPinBoard() {

    }

    private fun scrollToPosition(position:Int) {
        binding.recyclerViewPostList.scrollToPosition(position)
    }
}