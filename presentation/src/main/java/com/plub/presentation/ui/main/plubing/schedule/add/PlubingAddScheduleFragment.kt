package com.plub.presentation.ui.main.plubing.schedule.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingAddScheduleBinding
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.bottomSheet.BottomSheetSearchLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingAddScheduleFragment : BaseFragment<
        FragmentPlubingAddScheduleBinding, PlubingAddSchedulePageState, PlubingAddScheduleViewModel>(
    FragmentPlubingAddScheduleBinding::inflate
) {
    override val viewModel: PlubingAddScheduleViewModel by viewModels()



    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect { event ->
                    if(event is PlubingAddScheduleEvent) inspectEventFlow(event)
                }
            }
        }
    }

    private fun inspectEventFlow(event: PlubingAddScheduleEvent) {
        when(event) {
            is PlubingAddScheduleEvent.ShowStartDatePickerEvent -> {
                val startDate = viewModel.uiState.value.startDate
                startDate.apply {
                    showDatePickerDialog(this) { year, month, day ->  event.onClickOk(year, month + 1, day) }
                }
            }
            is PlubingAddScheduleEvent.ShowEndDatePickerEvent -> {
                val endDate = viewModel.uiState.value.endDate
                endDate.apply {
                    showDatePickerDialog(this) { year, month, day ->  event.onClickOk(year, month + 1, day) }
                }
            }
            is PlubingAddScheduleEvent.ShowStartTimePickerEvent -> {
                val startTime = viewModel.uiState.value.startTime
                startTime.apply {
                    showTimePickerDialog(this) { hour, min -> event.onClickOk(hour, min)  }
                }
            }
            is PlubingAddScheduleEvent.ShowEndTimePickerEvent -> {
                val endTime = viewModel.uiState.value.endTime
                endTime.apply {
                    showTimePickerDialog(this) { hour, min -> event.onClickOk(hour, min)  }
                }
            }
            is PlubingAddScheduleEvent.ShowBottomSheetSearchLocation -> {
                showBottomSheetLocation()
            }
        }
    }

    private fun showTimePickerDialog(initTime: Time, onSuccess: (hour: Int, min: Int) -> Unit) {
        val timePickerDialog = TimePickerDialog(requireActivity(),
                { _, hour, min ->
                    onSuccess(hour, min)
                },
            initTime.hour,
            initTime.minute,
                false
            )
        timePickerDialog.show()
    }

    private fun showDatePickerDialog(
        initDate: Date,
        onSuccess: (year: Int, month: Int, day: Int) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(requireContext(), R.style.PlubDatePickerStyle,
            { _, y, m, d ->
                onSuccess(y, m, d)
            },
            initDate.year,
            initDate.month - 1,
            initDate.day,
        )
        datePickerDialog.show()
    }

    private fun showBottomSheetLocation() {
        val bottomSheetSearchLocation = BottomSheetSearchLocation.newInstance { data ->
            viewModel.updateLocationData(data ?: KakaoLocationInfoDocumentVo())
        }
        bottomSheetSearchLocation.show(
            parentFragmentManager,
            bottomSheetSearchLocation.tag
        )
    }
}