package com.plub.presentation.ui.main.plubing.schedule

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingScheduleBinding
import com.plub.presentation.ui.main.plubing.PlubingMainEvent
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlubingScheduleFragment : BaseFragment<
        FragmentPlubingScheduleBinding, PlubingSchedulePageState, PlubingScheduleViewModel>(
    FragmentPlubingScheduleBinding::inflate
) {
    override val viewModel: PlubingScheduleViewModel by viewModels()

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
                    if(event is PlubingScheduleEvent)
                        inspectEventFlow(event)
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