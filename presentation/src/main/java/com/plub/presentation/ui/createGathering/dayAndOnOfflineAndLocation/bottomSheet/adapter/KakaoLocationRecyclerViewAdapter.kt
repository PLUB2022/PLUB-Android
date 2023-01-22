package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerKakaoLocationByKeywordBinding

class KakaoLocationRecyclerViewAdapter(
    private val itemClickEvent: (data: KakaoLocationInfoDocumentVo) -> Unit
) : PagingDataAdapter<KakaoLocationInfoDocumentVo, KakaoLocationRecyclerViewAdapter.KakaoLocationViewHolder>(
    DiffCallback()
) {
    private var selectedPlaceData: KakaoLocationInfoDocumentVo? = null
    private var prevSelectedPosition: Int = 0

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
        private var data: KakaoLocationInfoDocumentVo? = null
        private var position: Int? = null
        init {
            binding.constraintLayoutLocation.setOnClickListener {
                data?.let { data ->
                    position?.let { position ->
                        selectedPlaceData = data
                        binding.constraintLayoutLocation.setBackgroundResource(R.drawable.bg_rectangle_filled_white_width_2_radius_8_5f5ff9)
                        notifyItemChanged(prevSelectedPosition)
                        prevSelectedPosition = position
                        itemClickEvent(data)
                    }
                }
            }
        }
        fun bind(data: KakaoLocationInfoDocumentVo, position: Int) {
            binding.data = data
            this.data = data
            this.position = position

            if (selectedPlaceData == data)
                binding.constraintLayoutLocation.setBackgroundResource(R.drawable.bg_rectangle_filled_white_width_2_radius_8_5f5ff9)
            else
                binding.constraintLayoutLocation.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
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