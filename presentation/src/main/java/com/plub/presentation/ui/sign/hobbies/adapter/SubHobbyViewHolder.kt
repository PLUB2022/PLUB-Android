package com.plub.presentation.ui.sign.hobbies.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemSubHobbyBinding

class SubHobbyViewHolder(
    private val binding: IncludeItemSubHobbyBinding,
    private val listener: HobbiesAdapter.Delegate,
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: SubHobbyVo? = null
    private var isClicked:Boolean = false

    init {
        binding.root.setOnClickListener {
            vo?.let {
                listener.onClickSubHobby(isClicked, SelectedHobbyVo(it.parentHobbyId, it.id, it.name))
            }
        }
    }

    fun bind(item: SubHobbyVo) {
        vo = item
        binding.apply {
            isClicked = listener.selectedList.contains(SelectedHobbyVo(item.parentHobbyId, item.id, item.name))
            val textColorRes = if(isClicked) R.color.white else R.color.color_8c8c8c
            val backgroundRes = if(isClicked) R.drawable.bg_rectangle_filled_5f5ff9_radius_8 else R.drawable.bg_rectangle_empty_8c8c8c_radius_8

            textViewHobby.text = item.name
            textViewHobby.setTextColor(ContextCompat.getColor(root.context,textColorRes))
            root.setBackgroundResource(backgroundRes)
        }
    }
}