package com.plub.presentation.ui.main.plubing.schedule

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingScheduleBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.PlubingScheduleAdapter
import com.plub.presentation.ui.main.plubing.schedule.bottomSheet.BottomSheetScheduleDetail
import com.plub.presentation.util.infiniteScrolls
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingScheduleFragment : BaseFragment<
        FragmentPlubingScheduleBinding, PlubingSchedulePageState, PlubingScheduleViewModel>(
    FragmentPlubingScheduleBinding::inflate
) {
    private val scheduleAdapter: PlubingScheduleAdapter by lazy {
        PlubingScheduleAdapter(
            onClick = {
                viewModel.emitShowBottomSheetEvent(it)
            },
            onLongClick = {
                viewModel.emitShowBottomSheetDialogEditOrDeleteScheduleEvent(it)
            }
        )
    }

    override val viewModel: PlubingScheduleViewModel by viewModels()
    private val args: PlubingScheduleFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
            textViewPlubingName.text = args.plubingName

            recyclerViewSchedule.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = scheduleAdapter

                infiniteScrolls {
                    viewModel.fetchEntireScheduleUseCase()
                }
            }
        }

        viewModel.updatePlubbingId(args.plubingId)
        viewModel.fetchEntireScheduleUseCase()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect { event ->
                    if (event is PlubingScheduleEvent)
                        inspectEventFlow(event)
                }
            }

            launch {
                viewModel.uiState.collect {
                    scheduleAdapter.submitList(it.scheduleList)
                }
            }
        }
    }

    private fun inspectEventFlow(event: PlubingScheduleEvent) {
        when (event) {
            is PlubingScheduleEvent.GoToAddSchedule -> {
                val action =
                    PlubingScheduleFragmentDirections.actionPlubingScheduleToPlubingAddSchedule(
                        plubingId = args.plubingId,
                        plubingName = args.plubingName,
                        scheduleVo = event.scheduleVo
                    )
                findNavController().navigate(action)
            }

            is PlubingScheduleEvent.ShowBottomSheetScheduleDetail -> {
                showBottomSheetScheduleDetail(event.scheduleVo)
            }

            is PlubingScheduleEvent.ShowBottomSheetDialogEditOrDeleteSchedule -> {
                showBottomSheetDialogEditOrDeleteSchedule(event.scheduleVo)
            }
            is PlubingScheduleEvent.GoToBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun showBottomSheetScheduleDetail(scheduleVo: ScheduleVo) {
        val bottomSheetScheduleDetail = BottomSheetScheduleDetail.newInstance(
            scheduleVo = scheduleVo,
            okButtonClickEvent = { calendarId -> viewModel.putScheduleAttendYes(calendarId) },
            noButtonClickEvent = { calendarId -> viewModel.putScheduleAttendNo(calendarId) }
        )

        bottomSheetScheduleDetail.show(
            parentFragmentManager,
            bottomSheetScheduleDetail.tag
        )
    }

    private fun showBottomSheetDialogEditOrDeleteSchedule(scheduleVo: ScheduleVo) {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.SCHEDULE_TYPE) {
            viewModel.onClickImageMenuItemType(it, scheduleVo)
        }.show(parentFragmentManager, "")
    }
}