package com.plub.presentation.ui.createGathering.finish

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringFinishBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringFinishFragment : BaseFragment<
        FragmentCreateGatheringFinishBinding,
        CreateGatheringFinishPageState,
        CreateGatheringFinishViewModel>
    (
    FragmentCreateGatheringFinishBinding::inflate
) {
    override val viewModel: CreateGatheringFinishViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }
}