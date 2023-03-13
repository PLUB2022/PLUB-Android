package com.plub.presentation.ui.main.archive.bottomsheet.dots.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ArchiveMenuType
import com.plub.presentation.databinding.IncludeItemArchiveReportBinding
import com.plub.presentation.util.onThrottleClick

class ArchiveReportMenuViewHolder(
    private val binding: IncludeItemArchiveReportBinding,
    private val listener: ArchiveDotsMenuAdapter.ArchiveDotsMenuDelegate
) : RecyclerView.ViewHolder(binding.root) {

    var menuType : ArchiveMenuType? = null

    init {
        binding.root.onThrottleClick {
            menuType?.let { listener.onClick(it) }
        }
    }

    fun bind(type : ArchiveMenuType) {
        menuType = type
    }
}