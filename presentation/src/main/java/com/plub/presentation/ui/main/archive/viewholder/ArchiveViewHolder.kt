package com.plub.presentation.ui.main.archive.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.databinding.IncludeItemArchiveBinding
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter

class ArchiveViewHolder(
    private val binding: IncludeItemArchiveBinding,
    private val listener: ArchiveAdapter.ArchiveDelegate
) : RecyclerView.ViewHolder(binding.root) {


    init {

    }

    fun bind(item: ArchiveContentResponseVo) {
    }
}