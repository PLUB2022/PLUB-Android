package com.plub.presentation.ui.main.plubing.board.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentBoardDetailBinding
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.board.detail.adapter.BoardDetailAdapter
import com.plub.presentation.ui.main.plubing.board.write.BoardWriteFragment
import com.plub.presentation.util.getNavigationResult
import com.plub.presentation.util.hideKeyboard
import com.plub.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoardDetailFragment :
    BaseTestFragment<FragmentBoardDetailBinding, BoardDetailPageState, BoardDetailViewModel>(
        FragmentBoardDetailBinding::inflate
    ) {

    override val viewModel: BoardDetailViewModel by viewModels()

    private val boardDetailArgs: BoardDetailFragmentArgs by navArgs()

    private val boardDetailAdapter: BoardDetailAdapter by lazy {
        BoardDetailAdapter(object : BoardDetailAdapter.Delegate {

            override fun onClickNoticeMenu(vo: NoticeVo) {}
            override val noticeVo: NoticeVo = NoticeVo()

            override fun onClickBoardMenu(vo: PlubingBoardVo) {
                viewModel.onClickBoardMenu(vo)
            }

            override fun onClickCommentMenu(vo: BoardCommentVo) {
                viewModel.onClickCommentMenu(vo)
            }

            override fun onClickCommentReply(vo: BoardCommentVo) {
                viewModel.onClickCommentReply(vo)
            }

            override val boardVo: PlubingBoardVo
                get() = viewModel.uiState.boardVo.value
        })
    }


    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewBoardDetail.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = boardDetailAdapter

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

        viewModel.initArgs(boardDetailArgs.feedId)
        viewModel.onGetBoardDetail()
        viewModel.onGetBoardComments()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.commentList.collect {
                    boardDetailAdapter.submitList(it) {
                        viewModel.onBoardUpdated()
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as BoardDetailEvent)
                }
            }
        }

        getNavigationResult(BoardWriteFragment.KEY_RESULT_EDIT_COMPLETE) { vo: ParsePlubingBoardVo ->
            viewModel.onCompleteBoardEdit()
        }
    }

    private fun inspectEventFlow(event: BoardDetailEvent) {
        when(event) {
            is BoardDetailEvent.NotifyBoardDetailInfoNotify -> boardDetailAdapter.notifyBoardDetail()
            is BoardDetailEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.menuType, event.commentVo)
            is BoardDetailEvent.HideKeyboard -> hideKeyboard()
            is BoardDetailEvent.ShowKeyboard -> showKeyboard()
            is BoardDetailEvent.ScrollToPosition -> scrollToPosition(event.position)
            is BoardDetailEvent.GoToEditBoard -> goToEditBoard(event.feedId)
            is BoardDetailEvent.GoToReportBoard -> goToReport()
            is BoardDetailEvent.GoToReportComment -> goToReport()
            is BoardDetailEvent.Finish -> finish()
        }
    }

    private fun hideKeyboard() {
        binding.editTextComment.hideKeyboard()
    }

    private fun showKeyboard() {
        binding.root.postDelayed({
            binding.editTextComment.showKeyboard()
        },100)
    }

    private fun scrollToPosition(position:Int) {
        binding.recyclerViewBoardDetail.scrollToPosition(position)
    }

    private fun showMenuBottomSheetDialog(menuType: DialogMenuType, commentVo: BoardCommentVo) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, commentVo)
        }.show(parentFragmentManager, "")
    }

    private fun finish() {
        findNavController().popBackStack()
    }

    private fun goToEditBoard(feedId:Int) {
        val action = BoardDetailFragmentDirections.actionPlubingBoardDetailToPlubingBoardWrite(feedId = feedId, writeType = WriteType.EDIT)
        findNavController().navigate(action)
    }

    private fun goToReport() {

    }
}