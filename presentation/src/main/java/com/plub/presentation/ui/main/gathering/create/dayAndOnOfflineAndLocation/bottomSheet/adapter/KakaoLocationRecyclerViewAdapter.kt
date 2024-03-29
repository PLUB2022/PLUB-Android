package com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.bottomSheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.databinding.LayoutRecyclerKakaoLocationByKeywordBinding

class KakaoLocationRecyclerViewAdapter(
    private val listener: Delegate
) : PagingDataAdapter<KakaoLocationInfoDocumentVo, KakaoLocationViewHolder>(
    DiffCallback()
) {

    interface Delegate {
        fun onClickItem(data: KakaoLocationInfoDocumentVo, position: Int)
        val selectedVo:KakaoLocationInfoDocumentVo?
    }
    override fun onBindViewHolder(holder: KakaoLocationViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoLocationViewHolder {
        val binding = LayoutRecyclerKakaoLocationByKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KakaoLocationViewHolder(binding, listener)
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