package com.plub.presentation.ui.main.archive.bottomsheet.normal

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveNormalBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveNormalBottomSheetFragment(private val listener : ArchiveNormalBottomSheetDelegate) : BaseBottomSheetFragment<BottomSheetArchiveNormalBinding, PageState.Default, ArchiveNormalBottomSheetViewModel>(
    BottomSheetArchiveNormalBinding::inflate
) {

    interface ArchiveNormalBottomSheetDelegate{
        fun onClickReport()
    }

    override val viewModel: ArchiveNormalBottomSheetViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ArchiveNormalBottomSheetEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event : ArchiveNormalBottomSheetEvent){
        when(event){
            is ArchiveNormalBottomSheetEvent.GoToReport -> {
                listener.onClickReport()
                dismiss()
            }
        }
    }
}