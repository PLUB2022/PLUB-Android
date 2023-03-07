package com.plub.presentation.ui.main.plubing.board.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.databinding.IncludeItemBoardNormalBinding
import com.plub.presentation.util.GlideUtil

class PlubingBoardNormalViewHolder(
    private val binding: IncludeItemBoardNormalBinding,
    private val listener: PlubingBoardAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubingBoardVo? = null

    init {
        binding.root.setOnClickListener {
            vo?.let {
                listener.onClickBoard(it.feedId)
            }
        }

        binding.root.setOnLongClickListener {
            vo?.let {
                listener.onLongClickBoard(it.feedId, it.isHost, it.isAuthor)
            }
            true
        }
    }

    fun bind(item: PlubingBoardVo) {
        vo = item
        binding.apply {
            textViewNickname.text = item.nickname
            textViewTitle.text = item.title
            textViewDate.text = item.createAt
            textViewLike.text = item.likeCount.toString()
            textViewComment.text = item.commentCount.toString()

            when (item.feedType) {
                PlubingFeedType.TEXT -> bindTextFeedType(item)
                PlubingFeedType.IMAGE -> bindImageFeedType(item)
                PlubingFeedType.TEXT_AND_IMAGE -> bindTextAndImageFeedType(item)
            }
        }
    }

    private fun bindTextFeedType(item: PlubingBoardVo) {
        binding.apply {
            textViewContent.visibility = View.VISIBLE
            textViewContent.text = item.content
            imageViewTextAndImageType.visibility = View.GONE
            imageViewImageType.visibility = View.GONE
        }
    }

    private fun bindImageFeedType(item: PlubingBoardVo) {
        binding.apply {
            textViewContent.visibility = View.GONE
            imageViewTextAndImageType.visibility = View.GONE
            GlideUtil.loadImage(root.context, item.feedImage, imageViewImageType)
            imageViewImageType.visibility = View.VISIBLE
        }
    }

    private fun bindTextAndImageFeedType(item: PlubingBoardVo) {
        binding.apply {
            textViewContent.visibility = View.VISIBLE
            textViewContent.text = item.content
            imageViewTextAndImageType.visibility = View.VISIBLE
            GlideUtil.loadImage(root.context, item.feedImage, imageViewTextAndImageType)
            imageViewImageType.visibility = View.GONE
        }
    }
}