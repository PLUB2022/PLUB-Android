package com.plub.presentation.ui.main.plubing.board.write.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemWriteFeedTypeBinding

class WriteFeedTypeViewHolder(
    private val binding: IncludeItemWriteFeedTypeBinding,
    private val listener: WriteFeedTypeAdapter.Delegate,
) : RecyclerView.ViewHolder(binding.root) {

    private var type:PlubingFeedType? = null

    init {
        binding.root.setOnClickListener {
            type?.let {
                listener.onClickFeedType(it)
            }
        }
    }

    fun bind(item: WriteBoardFeedTypeVo) {
        type = item.feedType
        binding.apply {
            val textColorRes = if(item.isClicked) R.color.white else R.color.color_8c8c8c
            val backgroundRes = if(item.isClicked) R.drawable.bg_rectangle_filled_5f5ff9_radius_40 else R.drawable.bg_rectangle_empty_8c8c8c_radius_40

            textViewFeedType.text = root.context.getString(getFeedTypeTextRes(item.feedType))
            textViewFeedType.setTextColor(ContextCompat.getColor(root.context,textColorRes))
            root.setBackgroundResource(backgroundRes)
        }
    }

    private fun getFeedTypeTextRes(feedType: PlubingFeedType):Int {
        return when(feedType) {
            PlubingFeedType.TEXT -> R.string.plubing_board_write_feed_type_text
            PlubingFeedType.IMAGE -> R.string.plubing_board_write_feed_type_image
            PlubingFeedType.TEXT_AND_IMAGE -> R.string.plubing_board_write_feed_type_text_and_image
        }
    }
}