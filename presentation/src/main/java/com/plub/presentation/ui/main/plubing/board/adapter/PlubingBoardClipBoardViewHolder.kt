package com.plub.presentation.ui.main.plubing.board.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemBoardClipBoardBinding
import com.plub.presentation.databinding.IncludeItemClipBoardImageBinding
import com.plub.presentation.databinding.IncludeItemClipBoardTextBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px

class PlubingBoardClipBoardViewHolder(
    private val binding: IncludeItemBoardClipBoardBinding,
    private val listener: PlubingBoardAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val FLEX_GROW = 1f
        private const val FLEX_ITEM_MARGIN_HORIZONTAL = 6
        private const val FLEX_ITEM_MARGIN_VERTICAL = 4
    }

    private var vo: PlubingBoardVo? = null

    init {
        binding.root.setOnClickListener {
            listener.onClickClipBoard()
        }
    }

    fun bind(item: PlubingBoardVo) {
        vo = item

        binding.apply {
            flexBoxLayout.removeAllViews()
            listener.clipBoardList.forEachIndexed { index, plubingBoardVo ->
                val view = getFlexBoxView(plubingBoardVo, index)
                flexBoxLayout.addView(view)
            }
        }
    }

    private fun getFlexBoxView(vo: PlubingBoardVo, position: Int): View {
        return if (vo.feedType == PlubingFeedType.TEXT) getFlexBoxTextView(vo, position)
        else getFlexBoxImageView(vo, position)
    }

    private fun getFlexBoxImageView(vo: PlubingBoardVo, position: Int): View {
        val view = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.include_item_clip_board_image, null).apply {
            layoutParams = getFlexBoxLayoutParam(position)
        }

        return IncludeItemClipBoardImageBinding.bind(view).apply {
            GlideUtil.loadImage(root.context, vo.feedImage, imageView)
        }.root
    }

    private fun getFlexBoxTextView(vo: PlubingBoardVo, position: Int): View {
        val view = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.include_item_clip_board_text, null).apply {
            layoutParams = getFlexBoxLayoutParam(position)
        }

        return IncludeItemClipBoardTextBinding.bind(view).apply {
            textView.text = vo.content
        }.root
    }

    private fun getFlexBoxLayoutParam(position: Int): FlexboxLayout.LayoutParams {
        return FlexboxLayout.LayoutParams(0, 0).apply {
            flexGrow = FLEX_GROW
            isWrapBefore = isOddNumber(position)
            setMargins(
                FLEX_ITEM_MARGIN_HORIZONTAL.px, FLEX_ITEM_MARGIN_VERTICAL.px,
                FLEX_ITEM_MARGIN_HORIZONTAL.px, FLEX_ITEM_MARGIN_VERTICAL.px
            )
        }
    }

    private fun isOddNumber(position: Int) = position % 2 == 1
}