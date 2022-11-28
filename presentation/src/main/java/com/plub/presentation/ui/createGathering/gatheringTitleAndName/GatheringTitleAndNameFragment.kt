package com.plub.presentation.ui.createGathering.gatheringTitleAndName

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentGatheringTitleAndNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GatheringTitleAndNameFragment : BaseFragment<FragmentGatheringTitleAndNameBinding, PageState.Default, GatheringTitleAndNameViewModel>(
    FragmentGatheringTitleAndNameBinding::inflate
) {

    override val viewModel: GatheringTitleAndNameViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}