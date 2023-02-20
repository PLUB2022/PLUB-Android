package com.plub.presentation.ui.main.home.categoryChoice.filter

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryGatheringFilterBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GatheringFilterFragment :
    BaseFragment<FragmentCategoryGatheringFilterBinding, PageState.Default, GatheringFilterViewModel>(
        FragmentCategoryGatheringFilterBinding::inflate
    ) {

    private val gatheringFilterFragmentArgs : GatheringFilterFragmentArgs by navArgs()
    override val viewModel: GatheringFilterViewModel by viewModels()

    override fun initView() {
        binding.apply {
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