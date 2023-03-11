package com.plub.presentation.ui.main.archive.bottomsheet.normal

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveNormalBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.ArchiveDotsBottomSheetEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveNormalBottomSheetFragment(private val listener: ArchiveNormalDelegate) : BaseBottomSheetFragment<BottomSheetArchiveNormalBinding, PageState.Default, ArchiveNormalBottomSheetViewModel>(
    BottomSheetArchiveNormalBinding::inflate
) {

    interface ArchiveNormalDelegate{
        fun goToReport()
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
                    inspectEventFlow(it as ArchiveDotsBottomSheetEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event : ArchiveDotsBottomSheetEvent){
        when(event){
            is ArchiveDotsBottomSheetEvent.GoToReport -> {
                listener.goToReport()
                dismiss()
            }
            ArchiveDotsBottomSheetEvent.DeleteArchive -> {}
            ArchiveDotsBottomSheetEvent.EditArchive -> {}
        }
    }
}