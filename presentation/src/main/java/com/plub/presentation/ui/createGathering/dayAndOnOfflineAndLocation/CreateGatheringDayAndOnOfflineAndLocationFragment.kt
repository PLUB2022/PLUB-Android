package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringDayAndOnOfflineAndLocationBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringDayAndOnOfflineAndLocationFragment : BaseFragment<
        FragmentCreateGatheringDayAndOnOfflineAndLocationBinding,
        CreateGatheringDayAndOnOfflineAndLocationPageState,
        CreateGatheringDayAndOnOfflineAndLocationViewModel>(
    FragmentCreateGatheringDayAndOnOfflineAndLocationBinding::inflate
) {
    override val viewModel: CreateGatheringDayAndOnOfflineAndLocationViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {

        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }
}