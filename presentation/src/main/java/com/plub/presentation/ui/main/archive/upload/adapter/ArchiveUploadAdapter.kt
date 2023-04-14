package com.plub.presentation.ui.main.archive.upload.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.databinding.IncludeItemArchiveAddImageBinding
import com.plub.presentation.databinding.IncludeItemArchiveImageBinding
import com.plub.presentation.databinding.IncludeItemArchivePhotoTitleBinding
import com.plub.presentation.databinding.IncludeItemArchiveUpdateTitleBinding

class ArchiveUploadAdapter(private val listener: ArchiveUploadDelegate) : ListAdapter<ArchiveUploadVo, RecyclerView.ViewHolder>(
    ArchiveUploadDiffCallback()
) {

    interface ArchiveUploadDelegate {
        fun onClickDelete(image : String)
        fun addImage()
        fun onChangedText(text : String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArchiveUploadEditViewHolder -> holder.bind(currentList[position])
            is ArchiveUploadImageTextViewHolder -> holder.bind()
            is ArchiveUploadImageViewHolder -> holder.bind(currentList[position])
            is ArchiveUploadAddImageViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(ArchiveItemViewType.valueOf(viewType)){
            ArchiveItemViewType.EDIT_VIEW -> {
                val binding = IncludeItemArchiveUpdateTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArchiveUploadEditViewHolder(binding, listener)
            }
            ArchiveItemViewType.IMAGE_TEXT_VIEW -> {
                val binding = IncludeItemArchivePhotoTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArchiveUploadImageTextViewHolder(binding)
            }
            ArchiveItemViewType.IMAGE_VIEW -> {
                val binding = IncludeItemArchiveImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArchiveUploadImageViewHolder(binding, listener)
            }
            ArchiveItemViewType.IMAGE_ADD_VIEW -> {
                val binding = IncludeItemArchiveAddImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArchiveUploadAddImageViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class ArchiveUploadDiffCallback : DiffUtil.ItemCallback<ArchiveUploadVo>() {
    override fun areItemsTheSame(oldItem: ArchiveUploadVo, newItem: ArchiveUploadVo): Boolean =
        oldItem.viewType == newItem.viewType && oldItem.image == newItem.image
    override fun areContentsTheSame(oldItem: ArchiveUploadVo, newItem: ArchiveUploadVo): Boolean = oldItem == newItem
}