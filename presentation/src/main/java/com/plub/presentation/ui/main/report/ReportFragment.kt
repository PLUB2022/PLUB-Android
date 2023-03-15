package com.plub.presentation.ui.main.report

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentReportBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.report.adapter.ReportItemAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding, ReportState, ReportViewModel>(
    FragmentReportBinding::inflate
) {

    override val viewModel: ReportViewModel by viewModels()

    private val reportItemAdapter : ReportItemAdapter by lazy {
        ReportItemAdapter(object : ReportItemAdapter.Delegate{
            override fun onClickReport(type: Int) {
                viewModel.goToReportDetailPage(type)
            }

        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewReportList.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(8.px))
                adapter = reportItemAdapter
            }
        }
        viewModel.getReportList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    reportItemAdapter.submitList(it.reportList)
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
        }
    }

    private fun goToReportDetail(type : Int){
        val action = ReportFragmentDirections.actionReportToDetail(type)
        findNavController().navigate(action)
    }
}