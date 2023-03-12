package com.plub.presentation.ui.main.home.profile.active.myPost

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageAllMyPostBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageAllMyPostFragment :
    BaseFragment<FragmentMyPageAllMyPostBinding, MyPageAllMyPostState, MyPageAllMyPostViewModel>(
        FragmentMyPageAllMyPostBinding::inflate
    ) {

    private val myPageAllMyPostFragmentArgs : MyPageAllMyPostFragmentArgs by navArgs()

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
        viewModel.setPlubId(myPageAllMyPostFragmentArgs.plubbingId)
        viewModel.onFetchBoardList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    boardListAdapter.submitList(it.boardList)
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
            is MyPageAllMyPostEvent.ScrollToPosition -> scrollToPosition(event.position)
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
//        val action = PlubingMainFragmentDirections.actionPlubingMainToBoardPin()
//        findNavController().navigate(action)
    }

    private fun scrollToPosition(position:Int) {
        binding.recyclerViewPostList.scrollToPosition(position)
    }
}