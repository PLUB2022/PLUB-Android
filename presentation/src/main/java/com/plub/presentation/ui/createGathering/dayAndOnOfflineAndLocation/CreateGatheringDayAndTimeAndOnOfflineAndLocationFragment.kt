package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringDayAndTimeAndOnOfflineAndLocationBinding
import com.plub.presentation.ui.createGathering.CreateGatheringEvent
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.BottomSheetSearchLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringDayAndTimeAndOnOfflineAndLocationFragment : BaseFragment<
        FragmentCreateGatheringDayAndTimeAndOnOfflineAndLocationBinding,
        CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState,
        CreateGatheringDayAndTimeAndOnOfflineAndLocationViewModel>(
    FragmentCreateGatheringDayAndTimeAndOnOfflineAndLocationBinding::inflate
) {
    override val viewModel: CreateGatheringDayAndTimeAndOnOfflineAndLocationViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    private val timePickerDialog: TimePickerDialog by lazy {
        TimePickerDialog(
            requireActivity(),
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
                if (viewModel.uiState.value != CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect {
                    if (it is CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState)
                        viewModel.initUiState(it)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if(viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.setChildrenPageState(viewModel.uiState.value)
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    when(it) {
                        is CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent.ShowBottomSheetSearchLocation -> {
                            showBottomSheetLocation()
                        }

                        is CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent.ShowTimePickerDialog -> {
                            timePickerDialog.show()
                        }
                    }
                }
            }
        }
    }

    private fun showBottomSheetLocation() {
        val bottomSheetSearchLocation = BottomSheetSearchLocation { data ->
            viewModel.updateGatheringLocationData(data)
        }
        bottomSheetSearchLocation.show(
            requireActivity().supportFragmentManager,
            bottomSheetSearchLocation.tag
        )
    }
}