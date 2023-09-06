package com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation

import android.app.AlertDialog
import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringDayAndTimeAndOnOfflineAndLocationBinding
import com.plub.presentation.ui.main.gathering.create.CreateGatheringViewModel
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.bottomSheet.BottomSheetSearchLocation
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
            AlertDialog.THEME_HOLO_LIGHT,
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
                parentViewModel.childrenPageStateFlow.collect {
                    viewModel.initUiState(it)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is com.plub.presentation.ui.main.gathering.create.CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.setChildrenPageState(viewModel.uiState.value)
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    when (it) {
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
        val bottomSheetSearchLocation = BottomSheetSearchLocation.newInstance { data ->
            viewModel.updateGatheringLocationData(data)
        }
        bottomSheetSearchLocation.show(
            parentFragmentManager,
            bottomSheetSearchLocation.tag
        )
    }
}