package com.plub.presentation.ui.main.archive.bottomsheet.author


import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveAuthorBinding
import com.plub.presentation.databinding.BottomSheetArchiveHostBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.ArchiveDotsBottomSheetEvent
import com.plub.presentation.ui.main.archive.bottomsheet.host.ArchiveHostBottomSheetViewModel
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveAuthorBottomSheetFragment(private val plubbingId : Int, private val archiveId : Int, private val listener : ArchiveAuthorDelegate) : BaseBottomSheetFragment<BottomSheetArchiveAuthorBinding, PageState.Default, ArchiveAuthorBottomSheetViewModel>(
    BottomSheetArchiveAuthorBinding::inflate
) {

    interface ArchiveAuthorDelegate{
        fun onDelete()
        fun onClickEdit()
    }

    override val viewModel: ArchiveAuthorBottomSheetViewModel by viewModels()
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
            is ArchiveDotsBottomSheetEvent.GoToReport -> {}
            is ArchiveDotsBottomSheetEvent.DeleteArchive -> {
                listener.onDelete()
                dismiss()
            }
            is ArchiveDotsBottomSheetEvent.EditArchive -> {
                listener.onClickEdit()
            }
        }
    }
}