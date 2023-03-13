package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemArchiveAddImageBinding
import com.plub.presentation.util.onThrottleClick

class ArchiveUploadAddImageViewHolder(
    private val binding: IncludeItemArchiveAddImageBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.constraintLayoutAdd.onThrottleClick {
            listener.addImage()
        }
    }

    fun bind() {
    }
}