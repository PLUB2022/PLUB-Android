package com.plub.presentation.ui.main.archive.bottomsheet.author


import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveAuthorBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveAuthorBottomSheetFragment() : BaseBottomSheetFragment<BottomSheetArchiveAuthorBinding, PageState.Default, ArchiveAuthorBottomSheetViewModel>(
    BottomSheetArchiveAuthorBinding::inflate
) {

    interface ArchiveBottomSheetDelegate{

    }

    override val viewModel: ArchiveAuthorBottomSheetViewModel by viewModels()
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