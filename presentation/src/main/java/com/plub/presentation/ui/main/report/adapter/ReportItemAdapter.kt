package com.plub.presentation.ui.main.report.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.databinding.IncludeItemMemberBinding
import com.plub.presentation.databinding.IncludeItemReportContentBinding
import com.plub.presentation.ui.main.plubing.adapter.PlubingMemberViewHolder

class ReportItemAdapter(
    private val listener: Delegate,
) : ListAdapter<ReportItemVo, RecyclerView.ViewHolder>(ReportItemDiffCallBack()) {

    interface Delegate {
        fun onClickReport(type:Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReportItemViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemReportContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportItemViewHolder(binding, listener)
    }

}

class ReportItemDiffCallBack : DiffUtil.ItemCallback<ReportItemVo>() {
    override fun areItemsTheSame(oldItem: ReportItemVo, newItem: ReportItemVo): Boolean =
        oldItem.reportType == newItem.reportType

    override fun areContentsTheSame(oldItem: ReportItemVo, newItem: ReportItemVo): Boolean =
        oldItem == newItem
}