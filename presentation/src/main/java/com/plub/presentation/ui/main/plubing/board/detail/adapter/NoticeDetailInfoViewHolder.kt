package com.plub.presentation.ui.main.plubing.board.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.databinding.IncludeItemBoardDetailInfoBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class NoticeDetailInfoViewHolder(
    private val binding: IncludeItemBoardDetailInfoBinding,
    private val listener: BoardDetailAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: NoticeVo? = null

    init {
        binding.imageViewMenu.onThrottleClick {
            vo?.let {
                listener.onClickNoticeMenu(it)
            }
        }
    }

    fun bind(item: NoticeVo) {
        vo = item
        binding.apply {
            textViewContent.visibility = View.VISIBLE
            imageViewContent.visibility = View.GONE
            if (item.noticeType == NoticeType.APP) bindAppNotice() else bindPlubingNotice()

            textViewCommentCount.text = item.commentCount.toString()
            textViewLikeCount.text = item.likeCount.toString()
            textViewContent.text = item.content
            textViewDate.text = item.createAt
            textViewNickname.text = item.nickname
            textViewTitle.text = item.title
            GlideUtil.loadImage(root.context, item.profileImage, circleImageViewProfile)
        }
    }

    private fun bindAppNotice() {
        binding.apply {
            circleImageViewProfile.visibility = View.GONE
            imageViewComment.visibility = View.GONE
            imageViewLike.visibility = View.GONE
            imageViewMenu.visibility = View.GONE
            textViewLikeCount.visibility = View.GONE
            textViewNickname.visibility = View.GONE
            textViewCommentCount.visibility = View.GONE
        }
    }

    private fun bindPlubingNotice() {
        binding.apply {

        }
    }
}