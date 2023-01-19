package com.plub.presentation.ui.createGathering.preview

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPreviewBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringPreviewFragment : BaseFragment<
        FragmentCreateGatheringPreviewBinding, CreateGatheringPreviewPageState, CreateGatheringPreviewViewModel>(
    FragmentCreateGatheringPreviewBinding::inflate
) {
    override val viewModel: CreateGatheringPreviewViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }

        parentViewModel.updateChildrenPageState()
    }
}