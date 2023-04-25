package com.plub.presentation.ui.main.plubing.notice.detail

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
import com.plub.presentation.databinding.FragmentNoticeDetailBinding
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.board.detail.BoardDetailEvent
import com.plub.presentation.ui.main.plubing.board.detail.BoardDetailFragmentArgs
import com.plub.presentation.ui.main.plubing.board.detail.BoardDetailFragmentDirections
import com.plub.presentation.ui.main.plubing.board.detail.adapter.BoardDetailAdapter
import com.plub.presentation.ui.main.plubing.board.write.BoardWriteFragment
import com.plub.presentation.util.getNavigationResult
import com.plub.presentation.util.hideKeyboard
import com.plub.presentation.util.infiniteScrolls
import com.plub.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoticeDetailFragment :
    BaseTestFragment<FragmentNoticeDetailBinding, NoticeDetailPageState, NoticeDetailViewModel>(
        FragmentNoticeDetailBinding::inflate
    ) {

    override val viewModel: NoticeDetailViewModel by viewModels()

    private val noticeDetailArgs: NoticeDetailFragmentArgs by navArgs()

    private val noticeDetailAdapter: BoardDetailAdapter by lazy {
        BoardDetailAdapter(object : BoardDetailAdapter.Delegate {

            override fun onClickNoticeMenu(vo: NoticeVo) {
                viewModel.onClickNoticeMenu(vo)
            }

            override val noticeVo: NoticeVo get() = viewModel.uiState.noticeVo.value

            override fun onClickBoardMenu(vo: PlubingBoardVo) {}

            override fun onClickCommentMenu(vo: BoardCommentVo) {
                viewModel.onClickCommentMenu(vo)
            }

            override fun onClickCommentReply(vo: BoardCommentVo) {
                viewModel.onClickCommentReply(vo)
            }

            override val boardVo: PlubingBoardVo = PlubingBoardVo()
        }, true)
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewNoticeDetail.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = noticeDetailAdapter
                infiniteScrolls { viewModel.onScrollChanged() }
            }
        }

        viewModel.initArgs(noticeDetailArgs.noticeId, noticeDetailArgs.noticeType)
        viewModel.onGetNoticeDetail()
        viewModel.onGetNoticeComments()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.commentList.collect {
                    noticeDetailAdapter.submitList(it) {
                        viewModel.onNoticeUpdated()
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as NoticeDetailEvent)
                }
            }
        }

        getNavigationResult(BoardWriteFragment.KEY_RESULT_EDIT_COMPLETE) { vo: ParsePlubingBoardVo ->
            viewModel.onCompleteNoticeEdit()
        }
    }

    private fun inspectEventFlow(event: NoticeDetailEvent) {
        when(event) {
            is NoticeDetailEvent.NotifyBoardDetailInfoNotify -> noticeDetailAdapter.notifyBoardDetail()
            is NoticeDetailEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.menuType, event.commentVo)
            is NoticeDetailEvent.HideKeyboard -> hideKeyboard()
            is NoticeDetailEvent.ShowKeyboard -> showKeyboard()
            is NoticeDetailEvent.ScrollToPosition -> scrollToPosition(event.position)
            is NoticeDetailEvent.GoToEditNotice -> goToEditBoard(event.noticeId)
            is NoticeDetailEvent.GoToReportNotice -> goToReport()
            is NoticeDetailEvent.GoToReportComment -> goToReport()
            is NoticeDetailEvent.Finish -> finish()
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
        binding.recyclerViewNoticeDetail.scrollToPosition(position)
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
