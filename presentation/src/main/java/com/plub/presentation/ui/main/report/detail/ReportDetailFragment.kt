package com.plub.presentation.ui.main.report.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentReportDetailBinding
import com.plub.presentation.ui.main.report.ReportEvent
import com.plub.presentation.ui.main.report.adapter.ReportItemAdapter
import com.plub.presentation.util.onThrottleClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportDetailFragment : BaseFragment<FragmentReportDetailBinding, ReportDetailState, ReportDetailViewModel>(
    FragmentReportDetailBinding::inflate
) {

    override val viewModel: ReportDetailViewModel by viewModels()

    private val reportDetailFragmentArgs : ReportDetailFragmentArgs by navArgs()

    private val reportItemAdapter : ReportItemAdapter by lazy {
        ReportItemAdapter(object : ReportItemAdapter.Delegate{
            override fun onClickReport(type: Int) {
                viewModel.getReportList(type)
                binding.recyclerViewReportItem.visibility = View.GONE
            }

        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewReportItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = reportItemAdapter
            }

            constraintLayoutSpinner.onThrottleClick {
                recyclerViewReportItem.visibility = View.VISIBLE
            }
        }

        viewModel.getReportList(reportDetailFragmentArgs.type)
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
                }
            }
        }
    }

    private fun inspectEvent(event: ReportEvent){

    }

}