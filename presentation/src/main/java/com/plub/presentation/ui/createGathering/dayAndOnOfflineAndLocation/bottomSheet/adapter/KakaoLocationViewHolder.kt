package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerKakaoLocationByKeywordBinding

class KakaoLocationViewHolder(
    val binding: LayoutRecyclerKakaoLocationByKeywordBinding,
    val listener: KakaoLocationRecyclerViewAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: KakaoLocationInfoDocumentVo? = null
    private var position: Int? = null

    init {
        binding.constraintLayoutLocation.setOnClickListener {
            vo?.let { data ->
                position?.let { position ->
                    listener.onClickItem(data, position)
                }
            }
        }
    }

    fun bind(vo: KakaoLocationInfoDocumentVo, position: Int) {
        this.vo = vo
        this.position = position
        binding.apply {
            data = vo
            val resource = if (vo == listener.selectedVo) R.drawable.bg_rectangle_filled_white_width_2_radius_8_5f5ff9 else R.drawable.bg_rectangle_filled_white_radius_8
            constraintLayoutLocation.setBackgroundResource(resource)
        }
    }
}