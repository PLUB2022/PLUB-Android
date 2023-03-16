package com.plub.presentation.ui.main.archive.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemArchiveBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class ArchiveViewHolder(
    private val binding: IncludeItemArchiveBinding,
    private val listener: ArchiveAdapter.ArchiveDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: ArchiveContentResponseVo = ArchiveContentResponseVo()

    init {
        binding.constraintLayoutCard.onThrottleClick {
            listener.onCardClick(vo.archiveId)
        }

        binding.imageBtnDot.onThrottleClick {
            listener.onDotsClick(vo.accessType, vo.archiveId)
        }
    }

    fun bind(item: ArchiveContentResponseVo) {
        vo = item
        binding.apply {
            val imageList = arrayListOf(imageViewArchiveFirst, imageViewArchiveSecond, imageViewArchiveThird)
            for (position in 0 until item.images.size) {
                imageList[position].apply {
                    GlideUtil.loadImage(root.context, vo.images[position], this)
                    clipToOutline = true
                    isVisible = true
                }
            }
            textViewImageCount.text = item.images.size.toString()
            textViewTimeLine.text = root.context.getString(R.string.archive_sequence_date, item.sequence, item.createdAt)
            textViewArchiveTitle.text = item.title
        }
    }
}