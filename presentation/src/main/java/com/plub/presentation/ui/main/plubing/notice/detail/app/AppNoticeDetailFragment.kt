package com.plub.presentation.ui.main.plubing.notice.detail.app

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentAppNoticeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppNoticeDetailFragment : BaseTestFragment<FragmentAppNoticeDetailBinding, AppNoticeDetailState, AppNoticeDetailViewModel>(
    FragmentAppNoticeDetailBinding::inflate
) {

    private val appNoticeArgs: AppNoticeDetailFragmentArgs by navArgs()

    override val viewModel: AppNoticeDetailViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as AppNoticeDetailEvent)
                }
            }
        }

        viewModel.initArgs(appNoticeArgs.noticeId)
        viewModel.getNoticeDetail()
    }

    private fun inspectEventFlow(event: AppNoticeDetailEvent) {
        when (event) {
            AppNoticeDetailEvent.GoToBack -> findNavController().popBackStack()
        }
    }
}