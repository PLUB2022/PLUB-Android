package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemArchiveAddImageBinding

class ArchiveUploadAddImageViewHolder(
    private val binding: IncludeItemArchiveAddImageBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.constraintLayoutAdd.setOnClickListener {
            listener.addImage()
        }
    }

    fun bind() {
    }
}