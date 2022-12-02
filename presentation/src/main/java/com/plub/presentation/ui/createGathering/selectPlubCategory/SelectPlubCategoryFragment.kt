package com.plub.presentation.ui.createGathering.selectPlubCategory

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSelectPlubCategoryBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectPlubCategoryFragment : BaseFragment<FragmentSelectPlubCategoryBinding, PageState.Default, SelectPlubCategoryViewModel>(
    FragmentSelectPlubCategoryBinding::inflate
) {
    override val viewModel: SelectPlubCategoryViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }
}