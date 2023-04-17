package com.plub.presentation.ui.main.plubing.notice

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentNoticeBinding
import com.plub.presentation.parcelableVo.ParseNoticeVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.notice.adapter.NoticeAdapter
import com.plub.presentation.ui.main.plubing.notice.write.NoticeWriteFragment
import com.plub.presentation.util.getNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoticeFragment : BaseTestFragment<FragmentNoticeBinding, NoticePageState, NoticeViewModel>(
    FragmentNoticeBinding::inflate
) {

    private val noticeArgs: NoticeFragmentArgs by navArgs()

    override val viewModel: NoticeViewModel by viewModels()

    private val noticeAdapter: NoticeAdapter by lazy {
        NoticeAdapter(object : NoticeAdapter.Delegate {
            override fun onClickNotice(noticeId: Int) {
                viewModel.onClickNotice(noticeId)
            }

            override fun onLongClickNotice(noticeVo:NoticeVo) {
                viewModel.onLongClickNotice(noticeVo)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewNotice.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = noticeAdapter
                setHasFixedSize(true)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisiblePosition = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                        val isDownScroll = dy > 0
                        viewModel.onScrollChanged(isBottom, isDownScroll)
                    }
                })
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.noticeList.collect {
                    noticeAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as NoticeEvent)
                }
            }
        }

        getNavigationResult(NoticeWriteFragment.KEY_RESULT_EDIT_COMPLETE) { vo: ParseNoticeVo ->
            viewModel.onCompleteNoticeEdit(vo)
        }

        getNavigationResult(NoticeWriteFragment.KEY_RESULT_CREATE_COMPLETE) { _:String ->
            viewModel.onCompleteNoticeCreate()
        }

        viewModel.initArgs(noticeArgs.noticeType)
        viewModel.initTitle()
        viewModel.onGetNotice()
    }

    private fun inspectEventFlow(event: NoticeEvent) {
        when (event) {
            is NoticeEvent.GoToDetailNotice -> goToDetailNotice(event.noticeType, event.noticeId)
            is NoticeEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.menuType, event.noticeVo)
            is NoticeEvent.GoToWriteNotice -> goToWriteNotice(event.writeType, event.vo)
        }
    }

    private fun goToDetailNotice(noticeType: NoticeType, noticeId:Int) {
        val action = NoticeFragmentDirections.actionNoticeToNoticeDetail(noticeType, noticeId)
        findNavController().navigate(action)
    }

    private fun goToWriteNotice(writeType: WriteType, vo: ParseNoticeVo?) {
        val action = NoticeFragmentDirections.actionNoticeToNoticeWrite(writeType, vo)
        findNavController().navigate(action)
    }

    private fun showMenuBottomSheetDialog(menuType: DialogMenuType, noticeVo: NoticeVo) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, noticeVo)
        }.show(parentFragmentManager, "")
    }
}
