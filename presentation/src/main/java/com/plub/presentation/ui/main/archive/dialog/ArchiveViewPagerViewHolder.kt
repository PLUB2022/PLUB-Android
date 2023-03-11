package com.plub.presentation.ui.main.archive.dialog

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemDialogArchiveImageBinding
import com.plub.presentation.util.GlideUtil

class ArchiveViewPagerViewHolder(
    private val binding: IncludeItemDialogArchiveImageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: String) {
        binding.apply {
            GlideUtil.loadImage(root.context, item, imageViewArchive)
            imageViewArchive.clipToOutline = true
        }
    }
}