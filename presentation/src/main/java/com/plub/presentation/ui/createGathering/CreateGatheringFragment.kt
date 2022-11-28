package com.plub.presentation.ui.createGathering

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringFragment : BaseFragment<FragmentCreateGatheringBinding, PageState.Default, CreateGatheringViewModel>(
    FragmentCreateGatheringBinding::inflate
) {
    override val viewModel: CreateGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}