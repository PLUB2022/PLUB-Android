package com.plub.presentation.ui.main.gathering.modify

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.plub.domain.model.enums.ModifyGatheringPageType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGatheringBinding
import com.plub.presentation.databinding.IncludeTabModifyGatheringBinding
import com.plub.presentation.ui.main.gathering.modify.adapter.FragmentModifyGatheringPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyGatheringFragment : BaseFragment
<FragmentModifyGatheringBinding, ModifyGatheringPageState, ModifyGatheringViewModel>(
    FragmentModifyGatheringBinding::inflate
) {
    override val viewModel: ModifyGatheringViewModel by viewModels()

    private var pagerAdapter: FragmentModifyGatheringPagerAdapter? = null

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        viewModel.getGatheringInfoDetail(21) { id ->
            viewModel.getQuestions(id)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.uiState.collect { uiState ->
                    viewModel.handleUiState(uiState)
                }
            }

            launch {
                viewModel.eventFlow.collect { event ->
                }
            }

        }
    }
}