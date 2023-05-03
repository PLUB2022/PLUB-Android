package com.plub.presentation.ui.main.plubing.board.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubingBoardDetailType
import com.plub.domain.model.enums.PlubingCommentType
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.databinding.IncludeItemBoardDetailCommentNormalBinding
import com.plub.presentation.databinding.IncludeItemBoardDetailCommentReplyBinding
import com.plub.presentation.databinding.IncludeItemBoardDetailInfoBinding

class BoardDetailAdapter(
    val listener: Delegate,
    private val isNotice: Boolean = false
) : ListAdapter<BoardCommentVo, RecyclerView.ViewHolder>(BoardDetailDiffCallback()) {

    companion object {
        private const val BOARD_DETAIL_INFO_POSITION = 0
    }

    interface Delegate {
        fun onClickNoticeMenu(vo: NoticeVo)
        fun onClickBoardMenu(vo: PlubingBoardVo)
        fun onClickCommentMenu(vo: BoardCommentVo)
        fun onClickCommentReply(vo: BoardCommentVo)
        val boardVo: PlubingBoardVo
        val noticeVo: NoticeVo
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BoardDetailInfoViewHolder -> holder.bind(listener.boardVo)
            is BoardDetailCommentNormalViewHolder -> holder.bind(currentList[position])
            is BoardDetailCommentReplyViewHolder -> holder.bind(currentList[position])
            is NoticeDetailInfoViewHolder -> holder.bind(listener.noticeVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (PlubingBoardDetailType.indexOf(viewType)) {
            PlubingBoardDetailType.DETAIL -> {
                val binding = IncludeItemBoardDetailInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BoardDetailInfoViewHolder(binding, listener)
            }

            PlubingBoardDetailType.COMMENT -> {
                val binding = IncludeItemBoardDetailCommentNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BoardDetailCommentNormalViewHolder(binding, listener)
            }

            PlubingBoardDetailType.COMMENT_REPLY -> {
                val binding = IncludeItemBoardDetailCommentReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BoardDetailCommentReplyViewHolder(binding, listener)
            }

            PlubingBoardDetailType.DETAIL_NOTICE -> {
                val binding = IncludeItemBoardDetailInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NoticeDetailInfoViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == BOARD_DETAIL_INFO_POSITION -> {
                if(isNotice) PlubingBoardDetailType.DETAIL_NOTICE.idx
                else PlubingBoardDetailType.DETAIL.idx
            }
            currentList[position].commentType == PlubingCommentType.COMMENT -> PlubingBoardDetailType.COMMENT.idx
            else -> PlubingBoardDetailType.COMMENT_REPLY.idx
        }
    }

    fun notifyBoardDetail() {
        notifyItemChanged(BOARD_DETAIL_INFO_POSITION)
    }
}

class BoardDetailDiffCallback : DiffUtil.ItemCallback<BoardCommentVo>() {
    override fun areItemsTheSame(oldItem: BoardCommentVo, newItem: BoardCommentVo): Boolean =
        oldItem.commentId == newItem.commentId

    override fun areContentsTheSame(oldItem: BoardCommentVo, newItem: BoardCommentVo): Boolean =
        oldItem == newItem
}