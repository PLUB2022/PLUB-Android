package com.plub.presentation.ui.main.plubing.board.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.databinding.IncludeItemBoardSystemBinding
import com.plub.presentation.util.fromHtml

class PlubingBoardSystemViewHolder(
    private val binding: IncludeItemBoardSystemBinding,
    private val listener: PlubingBoardAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubingBoardVo? = null

    fun bind(item: PlubingBoardVo) {
        vo = item
        binding.apply {
            textViewTitle.text = item.title
            textViewContent.text = item.content.fromHtml()
            textViewDate.text = item.createAt
        }
    }
}