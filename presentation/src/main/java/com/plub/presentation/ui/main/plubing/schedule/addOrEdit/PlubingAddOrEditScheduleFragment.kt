package com.plub.presentation.ui.main.plubing.schedule.addOrEdit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.domain.model.enums.DialogCheckboxType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingAddOrEditScheduleBinding
import com.plub.presentation.ui.common.dialog.SelectCheckboxBottomSheetDialog
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.bottomSheet.BottomSheetSearchLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingAddOrEditScheduleFragment : BaseFragment<
        FragmentPlubingAddOrEditScheduleBinding, PlubingAddOrEditSchedulePageState, PlubingAddOrEditScheduleViewModel>(
    FragmentPlubingAddOrEditScheduleBinding::inflate
) {
    override val viewModel: PlubingAddOrEditScheduleViewModel by viewModels()
    private val args: PlubingAddOrEditScheduleFragmentArgs by navArgs()


    override fun initView() {
        binding.apply {
            vm = viewModel
            textViewPlubingName.text = args.plubingName
        }

        viewModel.updatePlubbingId(args.plubingId)
        viewModel.updatePageState(args.scheduleVo)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect { event ->
                    if(event is PlubingAddOrEditScheduleEvent) inspectEventFlow(event)
                }
            }
        }
    }

    private fun inspectEventFlow(event: PlubingAddOrEditScheduleEvent) {
        when(event) {
            is PlubingAddOrEditScheduleEvent.ShowStartDatePickerEventOrEdit -> {
                val startDate = viewModel.uiState.value.startScheduleDate
                startDate.apply {
                    showDatePickerDialog(this) { year, month, day ->  event.onClickOk(year, month + 1, day) }
                }
            }
            is PlubingAddOrEditScheduleEvent.ShowEndDatePickerEventOrEdit -> {
                val endDate = viewModel.uiState.value.endScheduleDate
                endDate.apply {
                    showDatePickerDialog(this) { year, month, day ->  event.onClickOk(year, month + 1, day) }
                }
            }
            is PlubingAddOrEditScheduleEvent.ShowStartTimePickerEventOrEdit -> {
                val startTime = viewModel.uiState.value.startScheduleTime
                startTime.apply {
                    showTimePickerDialog(this) { hour, min -> event.onClickOk(hour, min)  }
                }
            }
            is PlubingAddOrEditScheduleEvent.ShowEndTimePickerEventOrEdit -> {
                val endTime = viewModel.uiState.value.endScheduleTime
                endTime.apply {
                    showTimePickerDialog(this) { hour, min -> event.onClickOk(hour, min)  }
                }
            }
            is PlubingAddOrEditScheduleEvent.ShowBottomSheetSearchLocation -> {
                showBottomSheetLocation()
            }

            is PlubingAddOrEditScheduleEvent.ShowBottomSheetDialogSelectAlarm -> {
                showBottomSheetDialogSelectAlarm()
            }

            is PlubingAddOrEditScheduleEvent.GoToOrEditSchedule -> {
                val action = PlubingAddOrEditScheduleFragmentDirections.actionPlubingAddScheduleToPlubingSchedule(event.plubbingId, args.plubingName)
                findNavController().navigate(action)
            }
        }
    }

    private fun showTimePickerDialog(initScheduleTime: ScheduleTime, onSuccess: (hour: Int, min: Int) -> Unit) {
        val timePickerDialog = TimePickerDialog(requireActivity(),
                { _, hour, min ->
                    onSuccess(hour, min)
                },
            initScheduleTime.hour,
            initScheduleTime.minute,
                false
            )
        timePickerDialog.show()
    }

    private fun showDatePickerDialog(
        initScheduleDate: ScheduleDate,
        onSuccess: (year: Int, month: Int, day: Int) -> Unit
    ) {
        val datePickerDialog = DatePickerDialog(requireContext(), R.style.PlubDatePickerStyle,
            { _, y, m, d ->
                onSuccess(y, m, d)
            },
            initScheduleDate.year,
            initScheduleDate.month - 1,
            initScheduleDate.day,
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

    private fun showBottomSheetDialogSelectAlarm() {
        SelectCheckboxBottomSheetDialog.newInstance(
            checkboxType = DialogCheckboxType.ALARM_TYPE,
            title = requireContext().getString(R.string.word_alarm),
            icon = R.drawable.ic_alarm,
            selectedCheckboxType = viewModel.uiState.value.alarm
        ) {
            viewModel.updateAlarm(it)
        }.show(parentFragmentManager, "")
    }
}