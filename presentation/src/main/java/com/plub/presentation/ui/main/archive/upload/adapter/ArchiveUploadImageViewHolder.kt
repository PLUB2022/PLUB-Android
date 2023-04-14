package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.databinding.IncludeItemArchiveImageBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class ArchiveUploadImageViewHolder(
    private val binding: IncludeItemArchiveImageBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var image: String? = null

    init {
        binding.imageViewDelete.onThrottleClick {
            image?.let { listener.onClickDelete(it) }
        }

    }

    fun bind(item: ArchiveUploadVo) {
        image = item.image
        binding.apply {
            GlideUtil.loadImage(root.context, item.image, imageViewArchive)
            imageViewArchive.clipToOutline = true
        }
    }
}