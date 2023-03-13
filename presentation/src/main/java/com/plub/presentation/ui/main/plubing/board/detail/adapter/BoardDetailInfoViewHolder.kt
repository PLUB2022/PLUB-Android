package com.plub.presentation.ui.main.plubing.board.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.databinding.IncludeItemBoardDetailInfoBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class BoardDetailInfoViewHolder(
    private val binding: IncludeItemBoardDetailInfoBinding,
    private val listener: BoardDetailAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubingBoardVo? = null

    init {
        binding.imageViewMenu.onThrottleClick {
            vo?.let {
                listener.onClickBoardMenu(it)
            }
        }
    }

    fun bind(item: PlubingBoardVo) {
        vo = item
        contentVisibility(item.feedType)
        binding.apply {
            textViewCommentCount.text = item.commentCount.toString()
            textViewLikeCount.text = item.likeCount.toString()
            textViewContent.text = item.content
            textViewDate.text = item.createAt
            textViewNickname.text = item.nickname
            textViewTitle.text = item.title
            GlideUtil.loadImage(root.context, item.feedImage, imageViewContent)
            item.profileImage?.let { GlideUtil.loadImage(root.context, it, circleImageViewProfile) }
        }
    }

    private fun contentVisibility(feedType: PlubingFeedType) {
        binding.apply {
            when(feedType) {
                PlubingFeedType.TEXT -> {
                    textViewContent.visibility = View.VISIBLE
                    imageViewContent.visibility = View.GONE
                }
                PlubingFeedType.IMAGE -> {
                    textViewContent.visibility = View.GONE
                    imageViewContent.visibility = View.VISIBLE
                }
                PlubingFeedType.TEXT_AND_IMAGE -> {
                    textViewContent.visibility = View.VISIBLE
                    imageViewContent.visibility = View.VISIBLE
                }
            }
        }
    }
}