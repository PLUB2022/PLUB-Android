package com.plub.presentation.ui.main.plubing.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMemberBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class PlubingMemberViewHolder(
    private val binding: IncludeItemMemberBinding,
    private val listener: PlubingMemberAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubingMemberInfoVo? = null

    init {
        binding.root.onThrottleClick {
            vo?.let {
                listener.onClickProfile(it.id)
            }
        }
    }

    fun bind(item: PlubingMemberInfoVo) {
        vo = item
        binding.apply {
            textViewNickname.text = item.nickname
            item.profileImage?.let {
                GlideUtil.loadImage(root.context,
                    it,imageViewProfile, R.drawable.iv_default_profile)
            }
        }
    }
}