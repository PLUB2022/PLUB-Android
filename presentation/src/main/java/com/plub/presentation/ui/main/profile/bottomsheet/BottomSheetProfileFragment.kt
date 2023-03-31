package com.plub.presentation.ui.main.profile.bottomsheet

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetOtherProfileBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetProfileFragment :
    BaseBottomSheetFragment<BottomSheetOtherProfileBinding, PageState.Default, BottomSheetProfileViewModel>(
        BottomSheetOtherProfileBinding::inflate
    ) {

    override val viewModel: BottomSheetProfileViewModel by viewModels()

    override fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {

            }
        }
    }
}