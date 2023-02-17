package com.plub.presentation.ui.main.gathering.modifyGathering

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGatheringBinding

class ModifyGatheringFragment : BaseFragment
<FragmentModifyGatheringBinding, ModifyGatheringPageState, ModifyGatheringViewModel>(
    FragmentModifyGatheringBinding::inflate
) {
    override val viewModel: ModifyGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}