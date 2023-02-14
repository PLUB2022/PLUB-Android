package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.databinding.IncludeItemArchiveUpdateTitleBinding

class ArchiveUploadEditViewHolder(
    private val binding: IncludeItemArchiveUpdateTitleBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.editTextTitle.addTextChangedListener {
            //TODO 텍스트 감지
        }
    }

    fun bind() {
    }
}