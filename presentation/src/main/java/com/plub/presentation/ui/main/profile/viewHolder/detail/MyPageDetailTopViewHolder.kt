package com.plub.presentation.ui.main.profile.viewHolder.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringMyType
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

    fun bind(item: MyPageDetailTitleVo, visible : Boolean) {
        binding.apply {
            constraintLayoutGatheringStateBox.visibility = if(visible) View.VISIBLE else View.GONE
            when(item.viewType){
                MyPageGatheringMyType.HOST -> {
                    constraintLayoutGatheringStateBox.setBackgroundResource(R.drawable.bg_rectangle_filled_feefea_radius_6)
                    textViewGatheringState.text = root.context.getString(R.string.my_page_gathering_host)
                    textViewGatheringState.setTextColor(root.context.getColor(R.color.color_f75b2b))
                }
                MyPageGatheringMyType.GUEST -> {
                    constraintLayoutGatheringStateBox.setBackgroundResource(R.drawable.bg_rectangle_filled_e1e1fa_radius_6)
                    textViewGatheringState.text = root.context.getString(R.string.my_page_gathering_guest)
                    textViewGatheringState.setTextColor(root.context.getColor(R.color.color_5f5ff9))
                }
                MyPageGatheringMyType.END -> {
                    //TODO 종료는 진입이 가능한가?
                }
            }
            val days = item.date.joinToString(SEPARATOR_OF_DAY)
            val time = TimeFormatter.getAmPmHourMin(item.time)
            val date = root.context.getString(R.string.word_middle_line, days, time)

            textViewGatheringName.text = item.title
            textViewDate.text = date
            textViewPosition.text = item.position
        }
    }
}