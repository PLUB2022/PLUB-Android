package com.plub.presentation.ui.main.archive.upload.adapter

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.databinding.IncludeItemArchiveUpdateTitleBinding

class ArchiveUploadEditViewHolder(
    private val binding: IncludeItemArchiveUpdateTitleBinding,
    private val listener: ArchiveUploadAdapter.ArchiveUploadDelegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            editTextTitle.addTextChangedListener {
                listener.onChangedText(editTextTitle.text.toString())
            }
        }
    }

    fun bind(item: ArchiveUploadVo) {
        if(!item.editText.equals("")){
            binding.editTextTitle.setText(item.editText)
        }
    }
}