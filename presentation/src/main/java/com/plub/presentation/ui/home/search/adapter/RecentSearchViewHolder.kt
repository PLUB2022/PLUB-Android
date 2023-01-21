package com.plub.presentation.ui.home.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.presentation.databinding.IncludeItemRecentSearchBinding

class RecentSearchViewHolder(
    private val binding: IncludeItemRecentSearchBinding,
    private val listener: RecentSearchAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: RecentSearchVo? = null

    init {
        binding.root.setOnClickListener {
            vo?.let {
                listener.onClickDelete(it.id)
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