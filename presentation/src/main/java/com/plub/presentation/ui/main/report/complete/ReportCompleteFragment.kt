package com.plub.presentation.ui.main.report.complete

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentReportCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportCompleteFragment : BaseFragment<FragmentReportCompleteBinding, ReportCompleteState, ReportCompleteViewModel>(
    FragmentReportCompleteBinding::inflate
) {

    private val reportCompleteFragmentArgs : ReportCompleteFragmentArgs by navArgs()
    override val viewModel: ReportCompleteViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        viewModel.updateNowText(reportCompleteFragmentArgs.type)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as ReportCompleteEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ReportCompleteEvent){
        when(event){
            is ReportCompleteEvent.GoHome -> {goToHome()}
            is ReportCompleteEvent.GoBack -> {findNavController().popBackStack()}
        }
    }

    private fun goToHome(){
        val action = ReportCompleteFragmentDirections.actionReportCompleteToHome()
        findNavController().navigate(action)
    }
}