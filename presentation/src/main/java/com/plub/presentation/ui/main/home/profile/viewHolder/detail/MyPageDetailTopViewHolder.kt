package com.plub.presentation.ui.main.home.profile.viewHolder.detail

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyPageTopBinding
import com.plub.presentation.util.TimeFormatter

class MyPageDetailTopViewHolder(
    private val binding: IncludeItemMyPageTopBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val SEPARATOR_OF_DAY = ","
    }

    init {
    }

    fun bind(item: MyPageDetailTitleVo) {
        binding.apply {
            val days = item.date.joinToString(SEPARATOR_OF_DAY)
            val time = TimeFormatter.getAmPmHourMin(item.time)
            val date = root.context.getString(R.string.word_middle_line, days, time)

            textViewGatheringName.text = item.title
            textViewDate.text = date
            textViewPosition.text = item.position
        }
    }
}