package com.plub.presentation.ui.createGathering.gatheringTitleAndName

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringTitleAndNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringTitleAndNameFragment : BaseFragment<FragmentCreateGatheringTitleAndNameBinding, PageState.Default, CreateGatheringTitleAndNameViewModel>(
    FragmentCreateGatheringTitleAndNameBinding::inflate
) {

    override val viewModel: CreateGatheringTitleAndNameViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}