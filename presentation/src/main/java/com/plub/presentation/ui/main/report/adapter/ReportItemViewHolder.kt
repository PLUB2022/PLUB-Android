package com.plub.presentation.ui.main.report.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ReportBackgroundType
import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemReportContentBinding
import com.plub.presentation.util.onThrottleClick

class ReportItemViewHolder(
    private val binding: IncludeItemReportContentBinding,
    private val listener: ReportItemAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: ReportItemVo? = null

    init {
        binding.root.onThrottleClick {
            vo?.let {
                listener.onClickReport(it.reportType)
            }
        }
    }

    fun bind(item: ReportItemVo) {
        vo = item
        binding.apply{
            when(item.reportBackgroundType){
                ReportBackgroundType.BUTTON -> constraintLayoutParent.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_10_border_f2f3f4)
                ReportBackgroundType.SPINNER -> {} // default
            }
            textViewReportTitle.text = item.reportTitle
        }
    }
}