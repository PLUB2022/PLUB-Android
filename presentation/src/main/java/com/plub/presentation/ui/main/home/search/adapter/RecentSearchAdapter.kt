package com.plub.presentation.ui.main.home.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.presentation.databinding.IncludeItemRecentSearchBinding

class RecentSearchAdapter(
    private val listener: Delegate,
) : ListAdapter<RecentSearchVo, RecyclerView.ViewHolder>(RecentSearchDiffCallback()) {

    interface Delegate {
        fun onClickRecentSearch(text:String)
        fun onClickDelete(text: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentSearchViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchViewHolder(binding, listener)
    }
}

class RecentSearchDiffCallback : DiffUtil.ItemCallback<RecentSearchVo>() {
    override fun areItemsTheSame(oldItem: RecentSearchVo, newItem: RecentSearchVo): Boolean =
        oldItem.search == newItem.search

    override fun areContentsTheSame(oldItem: RecentSearchVo, newItem: RecentSearchVo): Boolean =
        oldItem == newItem
}