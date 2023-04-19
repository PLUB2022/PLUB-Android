package com.plub.presentation.ui.main.report

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.ReportReasonType
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentReportBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.report.adapter.ReportItemAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportFragment : BaseTestFragment<FragmentReportBinding, ReportState, ReportViewModel>(
    FragmentReportBinding::inflate
) {

    override val viewModel: ReportViewModel by viewModels()
    companion object{
        const val VERTICAL_SPACE = 8
    }

    private val reportFragmentArgs : ReportFragmentArgs by navArgs()
    private val reportItemAdapter : ReportItemAdapter by lazy {
        ReportItemAdapter(object : ReportItemAdapter.Delegate{
            override fun onClickReport(type: ReportReasonType) {
                viewModel.goToReportDetailPage(type)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewReportList.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = reportItemAdapter
            }
        }
        viewModel.getReportList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.reportList.collect{
                    reportItemAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as ReportEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ReportEvent){
        when(event){
            is ReportEvent.GoToReport -> { goToReportDetail(event.type)}
            is ReportEvent.GoToBack -> findNavController().popBackStack()
        }
    }

    private fun goToReportDetail(reasonType : ReportReasonType){
        val action = ReportFragmentDirections.actionReportToDetail(
            targetType = reportFragmentArgs.type,
            reasonType = reasonType
        )
        findNavController().navigate(action)
    }
}