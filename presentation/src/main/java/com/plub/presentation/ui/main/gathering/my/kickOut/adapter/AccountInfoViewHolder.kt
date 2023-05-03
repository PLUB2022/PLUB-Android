package com.plub.presentation.ui.main.gathering.my.kickOut.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerKickOutMemberBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class AccountInfoViewHolder(private val binding: LayoutRecyclerKickOutMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var onClickKickOut: (AccountInfoVo) -> Unit = {}
    private var accountInfo: AccountInfoVo? = null

    init {
        binding.buttonKickOut.onThrottleClick {
            accountInfo?.let{
                onClickKickOut(it)
            }
        }
    }

    fun bind(accountInfo: AccountInfoVo, onClickKickOut: (AccountInfoVo) -> Unit) {
        this.onClickKickOut = onClickKickOut
        this.accountInfo = accountInfo

        binding.apply {
            GlideUtil.loadImage(
                root.context,
                accountInfo.profileImage,
                imageViewProfile,
                R.drawable.iv_default_profile
            )
            imageViewProfile.clipToOutline = true
            textViewNickName.text = accountInfo.nickname
            executePendingBindings()
        }
    }
}