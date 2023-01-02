package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.databinding.LayoutRecyclerKakaoLocationByKeywordBinding
import com.plub.presentation.util.PlubLogger

class KakaoLocationRecyclerViewAdapter() : PagingDataAdapter<KakaoLocationInfoDocumentVo, KakaoLocationViewHolder>(
    DiffCallback()
) {
    override fun onBindViewHolder(holder: KakaoLocationViewHolder, position: Int) {
        PlubLogger.logD("test", "${getItem(position)}")
        getItem(position)?.let {  holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoLocationViewHolder {
        return KakaoLocationViewHolder(
            LayoutRecyclerKakaoLocationByKeywordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class DiffCallback : DiffUtil.ItemCallback<KakaoLocationInfoDocumentVo>() {
    override fun areItemsTheSame(
        oldItem: KakaoLocationInfoDocumentVo,
        newItem: KakaoLocationInfoDocumentVo
    ): Boolean {
        return oldItem.roadAddressName == newItem.roadAddressName
    }

    override fun areContentsTheSame(
        oldItem: KakaoLocationInfoDocumentVo,
        newItem: KakaoLocationInfoDocumentVo
    ): Boolean {
        return oldItem == newItem
    }

}

class KakaoLocationViewHolder(val binding: LayoutRecyclerKakaoLocationByKeywordBinding)
    : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: KakaoLocationInfoDocumentVo) {
            binding.data = data
        }
    }