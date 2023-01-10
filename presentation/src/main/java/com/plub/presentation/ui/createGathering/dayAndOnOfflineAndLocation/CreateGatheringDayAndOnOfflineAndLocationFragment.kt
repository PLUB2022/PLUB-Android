package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.fragment.app.viewModels
import com.plub.presentation.R
import com.plub.presentation.state.createGathering.CreateGatheringDayAndOnOfflineAndLocationPageState
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

    private val timePickerDialog: TimePickerDialog by lazy {
        TimePickerDialog(
            requireActivity(),
            R.style.PlubTimePickerStyle,
            { _, hour, min -> viewModel.setGatheringHourAndMinuteAndFormattedText(hour, min) },
            viewModel.uiState.value.gatheringHour,
            viewModel.uiState.value.gatheringMin,
            false
        )
    }

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
                if (viewModel.uiState.value != CreateGatheringDayAndOnOfflineAndLocationPageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect {
                    if (it is CreateGatheringDayAndOnOfflineAndLocationPageState)
                        viewModel.initUiState(it)
                }
            }

            launch {
                viewModel.showBottomSheetSearchLocation.collect {
                    val bottomSheetSearchLocation = BottomSheetSearchLocation { data ->
                        viewModel.updateGatheringLocationData(data)
                    }
                    bottomSheetSearchLocation.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetSearchLocation.tag
                    )
                }
            }

            launch {
                viewModel.showTimePickerDialog.collect {
                    timePickerDialog.show()
                }
            }
        }
    }
}