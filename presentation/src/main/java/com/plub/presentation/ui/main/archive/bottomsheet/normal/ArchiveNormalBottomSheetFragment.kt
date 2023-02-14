package com.plub.presentation.ui.main.archive.bottomsheet.normal

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveNormalBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveNormalBottomSheetFragment() : BaseBottomSheetFragment<BottomSheetArchiveNormalBinding, PageState.Default, ArchiveNormalBottomSheetViewModel>(
    BottomSheetArchiveNormalBinding::inflate
) {

    interface ArchiveBottomSheetDelegate{

    }

    override val viewModel: ArchiveNormalBottomSheetViewModel by viewModels()
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