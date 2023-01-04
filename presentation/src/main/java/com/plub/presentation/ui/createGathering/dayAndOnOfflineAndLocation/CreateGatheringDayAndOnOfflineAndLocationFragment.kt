package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringDayAndOnOfflineAndLocationBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.BottomSheetSearchLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringDayAndOnOfflineAndLocationFragment : BaseFragment<
        FragmentCreateGatheringDayAndOnOfflineAndLocationBinding,
        CreateGatheringDayAndOnOfflineAndLocationPageState,
        CreateGatheringDayAndOnOfflineAndLocationViewModel>(
    FragmentCreateGatheringDayAndOnOfflineAndLocationBinding::inflate
) {
    override val viewModel: CreateGatheringDayAndOnOfflineAndLocationViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {

        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.showBottomSheetSearchLocation.collect {
                    val bottomSheetSearchLocation = BottomSheetSearchLocation {
                        data -> viewModel.updateGatheringLocationData(data)
                    }
                    bottomSheetSearchLocation.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetSearchLocation.tag
                    )
                }
            }
        }
    }
}