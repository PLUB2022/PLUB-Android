package com.plub.presentation.ui.main.archive.bottomsheet.host

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveHostBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveHostBottomSheetFragment() : BaseBottomSheetFragment<BottomSheetArchiveHostBinding, PageState.Default, ArchiveHostBottomSheetViewModel>(
    BottomSheetArchiveHostBinding::inflate
) {

    interface ArchiveBottomSheetDelegate{

    }

    override val viewModel: ArchiveHostBottomSheetViewModel by viewModels()
    override fun initView() {
        binding.apply {

        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {

            }
        }
    }
}