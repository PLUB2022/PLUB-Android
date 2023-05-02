package com.plub.presentation.ui.main.gathering.my.kickOut.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerKickOutMemberBinding

class AccountInfoAdapter(private val onClickKickOut: (AccountInfoVo) -> Unit) :
    ListAdapter<AccountInfoVo, AccountInfoViewHolder>(AccountInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutRecyclerKickOutMemberBinding>(
            inflater,
            R.layout.layout_recycler_kick_out_member,
            parent,
            false
        )
        return AccountInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountInfoViewHolder, position: Int) {
        val accountInfo = getItem(position)
        holder.bind(accountInfo, onClickKickOut)
    }
}

class AccountInfoDiffCallback : DiffUtil.ItemCallback<AccountInfoVo>() {
    override fun areItemsTheSame(oldItem: AccountInfoVo, newItem: AccountInfoVo): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: AccountInfoVo, newItem: AccountInfoVo): Boolean {
        return oldItem == newItem
    }
}