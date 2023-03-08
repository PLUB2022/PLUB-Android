package com.plub.presentation.ui.main.plubing.schedule

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingScheduleBinding
import com.plub.presentation.ui.main.plubing.PlubingMainEvent
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentDirections
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import com.plub.presentation.ui.main.plubing.schedule.adapter.PlubingScheduleAdapter
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.infiniteScrolls
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingScheduleFragment : BaseFragment<
        FragmentPlubingScheduleBinding, PlubingSchedulePageState, PlubingScheduleViewModel>(
    FragmentPlubingScheduleBinding::inflate
) {
    private val scheduleAdapter: PlubingScheduleAdapter by lazy {
        PlubingScheduleAdapter()
    }

    override val viewModel: PlubingScheduleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewSchedule.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = scheduleAdapter

                infiniteScrolls {
                    viewModel.fetchEntireScheduleUseCase()
                }
            }
        }

        viewModel.fetchEntireScheduleUseCase()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect { event ->
                    if(event is PlubingScheduleEvent)
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
                val action = PlubingScheduleFragmentDirections.actionPlubingScheduleToPlubingAddSchedule(
                    event.id
                )
                findNavController().navigate(action)
            }
        }
    }
}