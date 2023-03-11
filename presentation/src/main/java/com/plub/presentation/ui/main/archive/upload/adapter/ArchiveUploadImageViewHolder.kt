package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.databinding.IncludeItemArchiveImageBinding
import com.plub.presentation.util.GlideUtil

class ArchiveUploadImageViewHolder(
    private val binding: IncludeItemArchiveImageBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var myPosition : Int = 0

    init {
        binding.imageViewDelete.setOnClickListener {
            listener.onClickDelete(myPosition)
        }

    }

    fun bind(item: ArchiveUploadVo, position : Int) {
        myPosition = position
        binding.apply {
            GlideUtil.loadImage(root.context, item.image, imageViewArchive)
            imageViewArchive.clipToOutline = true
        }
    }
}