package com.plub.presentation.ui.main.archive.bottomsheet.upload

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveUploadBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveBottomSheetFragment : BaseBottomSheetFragment<BottomSheetArchiveUploadBinding, PageState.Default, ArchiveBottomSheetViewModel>(
    BottomSheetArchiveUploadBinding::inflate
) {

    override val viewModel: ArchiveBottomSheetViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ArchiveBottomSheetEvent)
                }
            }


        }
    }

    private fun inspectEventFlow(event : ArchiveBottomSheetEvent){

    }
}