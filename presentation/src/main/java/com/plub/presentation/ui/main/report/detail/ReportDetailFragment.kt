package com.plub.presentation.ui.main.report.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentReportDetailBinding
import com.plub.presentation.ui.main.report.adapter.ReportItemAdapter
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
                viewModel.onClickSpinner()
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewReportItem.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                adapter = reportItemAdapter
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
                    inspectEvent(it as ReportDetailEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ReportDetailEvent){
        when(event){
            ReportDetailEvent.GoneSpinner -> binding.recyclerViewReportItem.visibility = View.GONE
            ReportDetailEvent.ShowSpinner -> binding.recyclerViewReportItem.visibility = View.VISIBLE
            ReportDetailEvent.BorderBlack -> binding.editTextReportContent.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8_border_black)
            ReportDetailEvent.BorderDefault -> binding.editTextReportContent.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_10_border_f2f3f4)
        }
    }

}