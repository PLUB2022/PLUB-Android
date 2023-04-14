package com.plub.presentation.ui.main.gathering.my.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringContentBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.setTopCorner

class MyGatheringsViewHolder(
    private val binding: LayoutRecyclerMyGatheringContentBinding,
    private val onContentClick: (plubbingId: Int) -> Unit = { },
    private val onMyGatheringMeatBallClick: (view: View, plubbingId: Int) -> Unit = { _, _ -> },
    private val onMyHostingMeatBallClick: (view: View, plubbingId: Int) -> Unit = { _, _ -> },
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val RADIUS = 30
    }

    private val context = binding.root.context
    private var plubbingId: Int? = null

    init {
        binding.root.onThrottleClick {
            plubbingId?.let {
                onContentClick(it)
            }
        }

        binding.imageViewMeatBall.onThrottleClick { view ->
            plubbingId?.let { id ->
                onMyGatheringMeatBallClick(view, id)
                onMyHostingMeatBallClick(view, id)
            }
        }
    }

    fun bind(item: MyGatheringResponseVo) {
        plubbingId = item.plubbingId

        binding.apply {
            GlideUtil.loadImage(binding.root.context, item.mainImage, imageViewMain)
            imageViewMain.setTopCorner(RADIUS)
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