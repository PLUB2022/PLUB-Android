package com.plub.presentation.ui.main.profile.bottomsheet.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemOtherProfileBinding
import com.plub.presentation.util.GlideUtil

class OtherProfileDataViewHolder(
    private val binding: IncludeItemOtherProfileBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {
    }

    fun bind(item: MyInfoResponseVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            textViewProfileNickname.text = item.nickname
            textViewProfileIntro.text = item.introduce
            textViewTodoTitle.text = root.context.getString(R.string.profile_todo_title, item.nickname)
        }
    }
}