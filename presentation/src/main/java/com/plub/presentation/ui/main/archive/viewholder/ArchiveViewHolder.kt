package com.plub.presentation.ui.main.archive.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemArchiveBinding
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.util.GlideUtil

class ArchiveViewHolder(
    private val binding: IncludeItemArchiveBinding,
    private val listener: ArchiveAdapter.ArchiveDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: ArchiveContentResponseVo = ArchiveContentResponseVo()

    init {
        binding.constraintLayoutCard.setOnClickListener {
            listener.onCardClick(vo.archiveId)
        }
    }

    fun bind(item: ArchiveContentResponseVo) {
        vo = item
        binding.apply {
            for (position in 0..item.images.size) {
                setImageView(position)
            }
            val imageCount = item.images.size.toString()
            textViewImageCount.text = imageCount
            textViewTimeLine.text = root.context.getString(R.string.archive_sequence_date, item.sequence, item.createdAt)
            textViewArchiveTitle.text = item.title
        }
    }

    fun setImageView(position: Int) {
        binding.apply {
            when (position) {
                0 -> {
                    GlideUtil.loadImage(root.context, vo.images[position], imageViewArchiveFirst)
                    imageViewArchiveFirst.apply {
                        clipToOutline = true
                        isVisible = true
                    }
                }
                1 -> {
                    GlideUtil.loadImage(root.context, vo.images[position], imageViewArchiveSecond)
                    imageViewArchiveSecond.apply {
                        clipToOutline = true
                        isVisible = true
                    }
                }
                2 -> {
                    GlideUtil.loadImage(root.context, vo.images[position], imageViewArchiveThird)
                    imageViewArchiveThird.apply {
                        clipToOutline = true
                        isVisible = true
                    }
                }
            }
        }
    }
}