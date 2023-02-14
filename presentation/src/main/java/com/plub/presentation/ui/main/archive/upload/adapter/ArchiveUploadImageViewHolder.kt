package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.databinding.IncludeItemArchiveImageBinding

class ArchiveUploadImageViewHolder(
    private val binding: IncludeItemArchiveImageBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {


    init {

    }

    fun bind(item: ArchiveUploadVo) {
    }
}