package com.plub.presentation.ui.main.plubing.notice.write

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentNoticeWriteBinding
import com.plub.presentation.util.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoticeWriteFragment : BaseTestFragment<FragmentNoticeWriteBinding, NoticeWritePageState, NoticeWriteViewModel>(
    FragmentNoticeWriteBinding::inflate
) {

    companion object {
        const val KEY_RESULT_EDIT_COMPLETE = "KEY_RESULT_EDIT_COMPLETE"
        const val KEY_RESULT_CREATE_COMPLETE = "KEY_RESULT_CREATE_COMPLETE"
    }

    private val noticeWriteArgs: NoticeWriteFragmentArgs by navArgs()

    override val viewModel: NoticeWriteViewModel by viewModels()

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
                    inspectEventFlow(it as NoticeWriteEvent)
                }
            }
        }

        viewModel.initArgs(noticeWriteArgs)
    }

    private fun inspectEventFlow(event: NoticeWriteEvent) {
        when (event) {
            is NoticeWriteEvent.CompleteCreate -> {
                setNavigationResult(KEY_RESULT_CREATE_COMPLETE, "")
                findNavController().popBackStack()
            }
            is NoticeWriteEvent.CompleteEdit -> {
                setNavigationResult(KEY_RESULT_EDIT_COMPLETE, event.notice)
                findNavController().popBackStack()
            }
        }
    }
}
