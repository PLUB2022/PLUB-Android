package com.plub.presentation.ui.main.gathering.my.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringContentBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.onThrottleClick

class MyGatheringsViewHolder(
    private val binding: LayoutRecyclerMyGatheringContentBinding,
    private val onMyGatheringMeatBallClick: () -> Unit = { },
    private val onMyHostingMeatBallClick: () -> Unit = { },
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    init {
        binding.imageViewMeatBall.onThrottleClick {
            onMyGatheringMeatBallClick()
            onMyHostingMeatBallClick()
        }
    }

    fun bind(item: MyGatheringResponseVo) {
        binding.apply {
            imageViewMain.clipToOutline = true
            GlideUtil.loadImage(binding.root.context, item.mainImage, imageViewMain)
            textViewGoal.text = item.goal
            textViewTitle.text = item.name
            textViewTime.text = context.getString(R.string.word_line_space, getDayText(item.days), TimeFormatter.get_ah_colon_mm(item.time))
        }

        binding.executePendingBindings()
    }

    private fun getDayText(days: List<String>): String {
        val dayTypes = mutableListOf<DaysType>()
        days.forEach {
            dayTypes.add(DaysType.findByEng(it))
        }

        dayTypes.sortBy { it.idx }

        val daysText = dayTypes.joinToString(",") { it.kor }

        return when {
            dayTypes.size == DaysType.values().size ||
                    DaysType.ALL in dayTypes -> context.getString(R.string.word_every_day)
            dayTypes.size == 1 -> context.getString(R.string.word_every_week_day, daysText)
            else -> context.getString(R.string.word_every_week, daysText)
        }
    }
}