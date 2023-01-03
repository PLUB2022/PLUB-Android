package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.databinding.LayoutRecyclerKakaoLocationByKeywordBinding
import com.plub.presentation.util.PlubLogger

class KakaoLocationRecyclerViewAdapter(
    private val itemClickEvent: (placeName: String) -> Unit
) : PagingDataAdapter<KakaoLocationInfoDocumentVo, KakaoLocationRecyclerViewAdapter.KakaoLocationViewHolder>(
    DiffCallback()
) {
    private var selectedPlaceName = ""
    private var prevSelectedPosition = 0
    override fun onBindViewHolder(holder: KakaoLocationViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data, position)
        }
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

    inner class KakaoLocationViewHolder(val binding: LayoutRecyclerKakaoLocationByKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: KakaoLocationInfoDocumentVo, position: Int) {
            binding.data = data
            binding.root.setOnClickListener {
                selectedPlaceName = data.placeName
                binding.textViewPlaceName.textSize = 30f
                notifyItemChanged(prevSelectedPosition)
                prevSelectedPosition = position
            }
            if (selectedPlaceName == data.placeName)
                binding.textViewPlaceName.textSize = 30f
            else
                binding.textViewPlaceName.textSize = 10f
        }
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