package com.plub.presentation.ui.main.home.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.presentation.databinding.IncludeItemRecentSearchBinding
import com.plub.presentation.util.onThrottleClick

class RecentSearchViewHolder(
    private val binding: IncludeItemRecentSearchBinding,
    private val listener: RecentSearchAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: RecentSearchVo? = null

    init {
        binding.root.onThrottleClick {
            vo?.let {
                listener.onClickRecentSearch(it.search)
            }
        }

        binding.imageViewDeleteIcon.onThrottleClick {
            vo?.let {
                listener.onClickDelete(it.search)
            }
        }
    }

    fun bind(item: RecentSearchVo) {
        vo = item
        binding.apply {
            textViewSearch.text = item.search
        }
    }
}