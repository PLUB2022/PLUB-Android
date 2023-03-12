package com.plub.presentation.ui.main.plubing.board.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemBoardDetailCommentReplyBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class BoardDetailCommentReplyViewHolder(
    private val binding: IncludeItemBoardDetailCommentReplyBinding,
    private val listener: BoardDetailAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: BoardCommentVo? = null

    init {
        binding.imageViewMenu.onThrottleClick {
            vo?.let {
                listener.onClickCommentMenu(it)
            }
        }

        binding.textViewReply.onThrottleClick {
            vo?.let {
                listener.onClickCommentReply(it)
            }
        }
    }

    fun bind(item: BoardCommentVo) {
        vo = item
        binding.apply {
            textViewMarkAuthor.visibility = if(item.isFeedAuthor) View.VISIBLE else View.GONE
            textViewDate.text = item.createAt
            textViewNickname.text = item.nickname
            textViewContent.text = item.content
            textViewReplyParent.text = root.context.getString(R.string.plubing_board_detail_replied, item.parentCommentNickname)
            GlideUtil.loadImage(root.context, item.profileImage, circleImageViewProfile)
        }
    }
}