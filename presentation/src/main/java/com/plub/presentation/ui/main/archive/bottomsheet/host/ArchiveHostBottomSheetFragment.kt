package com.plub.presentation.ui.main.archive.bottomsheet.host

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveHostBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.ArchiveDotsBottomSheetEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveHostBottomSheetFragment(private val plubbingId : Int, private val archiveId : Int, private val listener : ArchiveHostDelegate) : BaseBottomSheetFragment<BottomSheetArchiveHostBinding, PageState.Default, ArchiveHostBottomSheetViewModel>(
    BottomSheetArchiveHostBinding::inflate
) {

    interface ArchiveHostDelegate{
        fun onDelete()
        fun goToReport()
    }

    override val viewModel: ArchiveHostBottomSheetViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
        }
        viewModel.setId(plubbingId, archiveId)
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
            ArchiveDotsBottomSheetEvent.DeleteArchive -> {
                listener.onDelete()
                dismiss()
            }
            ArchiveDotsBottomSheetEvent.EditArchive -> {}
        }
    }
}