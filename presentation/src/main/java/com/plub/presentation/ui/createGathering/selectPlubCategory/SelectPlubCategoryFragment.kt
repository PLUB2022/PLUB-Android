package com.plub.presentation.ui.createGathering.selectPlubCategory

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSelectPlubCategoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectPlubCategoryFragment : BaseFragment<FragmentSelectPlubCategoryBinding, PageState.Default, SelectPlubCategoryViewModel>(
    FragmentSelectPlubCategoryBinding::inflate
) {
    override val viewModel: SelectPlubCategoryViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}