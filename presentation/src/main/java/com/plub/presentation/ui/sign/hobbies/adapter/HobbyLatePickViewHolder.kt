package com.plub.presentation.ui.sign.hobbies.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemHobbyLatePickBinding
import com.plub.presentation.util.onThrottleClick

class HobbyLatePickViewHolder(
    private val binding: IncludeItemHobbyLatePickBinding,
    private val listener: HobbiesAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.onThrottleClick {
            listener.onClickLatePick()
        }
    }

    fun bind() {

    }
}