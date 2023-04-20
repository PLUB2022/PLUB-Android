package com.plub.presentation.ui.main.report.alarm

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentAlarmReportBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportAlarmFragment : BaseTestFragment<FragmentAlarmReportBinding, ReportAlarmState, ReportAlarmViewModel>(
    FragmentAlarmReportBinding::inflate
) {

    override val viewModel: ReportAlarmViewModel by viewModels()

    private val reportAlarmFragmentArgs : ReportAlarmFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
        viewModel.getReportDetail(reportAlarmFragmentArgs.reportId)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as ReportAlarmEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ReportAlarmEvent){
        when(event){
            is ReportAlarmEvent.GoToBack -> findNavController().popBackStack()
        }
    }
}