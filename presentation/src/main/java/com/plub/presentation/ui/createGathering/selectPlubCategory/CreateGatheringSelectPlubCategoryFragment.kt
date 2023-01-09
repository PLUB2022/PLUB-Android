package com.plub.presentation.ui.createGathering.selectPlubCategory

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringSelectPlubCategoryBinding
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateGatheringSelectPlubCategoryFragment : BaseFragment<FragmentCreateGatheringSelectPlubCategoryBinding, PageState.Default, CreateGatheringSelectPlubCategoryViewModel>(
    FragmentCreateGatheringSelectPlubCategoryBinding::inflate
) {
    override val viewModel: CreateGatheringSelectPlubCategoryViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }
}