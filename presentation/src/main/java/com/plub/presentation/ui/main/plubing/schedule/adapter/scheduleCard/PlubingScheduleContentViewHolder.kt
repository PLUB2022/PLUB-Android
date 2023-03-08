package com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ScheduleCardType
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleContentBinding
import com.plub.presentation.ui.common.decoration.OverlapDecoration
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.profile.PlubingScheduleProfileAdapter
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.TimeFormatter

class PlubingScheduleContentViewHolder(
    private val binding: LayoutRecyclerPlubingScheduleContentBinding,
    private val onClick: (() -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val OVERLAP_WIDTH = 8
    }

    private val plubingScheduleProfileAdapter: PlubingScheduleProfileAdapter by lazy {
        PlubingScheduleProfileAdapter()
    }

    init {
        binding.root.setOnClickListener {
            onClick?.invoke()
        }

        binding.recyclerViewProfile.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = plubingScheduleProfileAdapter
            addItemDecoration(OverlapDecoration(OVERLAP_WIDTH))
            itemAnimator = null
        }
    }

    fun bind(items: List<ScheduleVo>, position: Int) {
        val currentItem = items[position]
        binding.apply {
            textViewTitle.text = currentItem.title
            setTextViewMonth(textViewMonth, items, position)
            textViewDate.text = getTextViewDate(currentItem)
            textViewTime.text = getTextViewTime(currentItem)
            setLocation(textViewLocation, imageViewLocation, currentItem)
            setTextColor(currentItem.startedAt)
            viewDivider1.visibility = if(position == 1) View.INVISIBLE else View.VISIBLE
        }


        val profileList = currentItem.calendarAttendList.calendarAttendList.map { it.profileImage }
        plubingScheduleProfileAdapter.submitList(profileList)
    }

    private fun setTextViewMonth(textViewMonth: TextView, items: List<ScheduleVo>, position: Int) {
        val firstStartMonth = TimeFormatter.getIntMonthFromyyyyDashmmDashddFormat(items[position].startedAt)
        val secondStartMonth = TimeFormatter.getIntMonthFromyyyyDashmmDashddFormat(items.prevItem(position).startedAt)

        if(firstStartMonth != secondStartMonth || items.prevItemTypeIsContent(position).not()) {
            textViewMonth.visibility = View.VISIBLE
            textViewMonth.text = binding.root.context.getString(R.string.word_birth_month,firstStartMonth.toString())
        } else {
            textViewMonth.visibility = View.GONE
        }
    }

    private fun List<ScheduleVo>.prevItemTypeIsContent(index: Int): Boolean {
        return this.size > 1 && this[index - 1].viewType == ScheduleCardType.CONTENT
    }

    private fun List<ScheduleVo>.prevItem(index: Int): ScheduleVo {
        return if(this.size > 1)
            this[index - 1]
        else ScheduleVo()
    }

    private fun getTextViewTime(item: ScheduleVo): String {
        val startTime = TimeFormatter.get_ah_colon_mm(item.startTime)
        val endTime = TimeFormatter.get_ah_colon_mm(item.endTime)
        return binding.root.context.getString(R.string.word_middle_hyphen, startTime, endTime)
    }

    private fun getTextViewDate(item: ScheduleVo): String {
        val day = TimeFormatter.getStringDayFromyyyyDashmmDashddFormat(item.startedAt)
        return binding.root.context.getString(R.string.word_birth_day, day)
    }

    private fun setLocation(textViewLocation: TextView, imageViewLocation: ImageView, item: ScheduleVo) {
        if(item.placeName.isEmpty()) {
            textViewLocation.visibility = View.GONE
            imageViewLocation.visibility = View.GONE
        } else textViewLocation.text = item.placeName
    }

    private fun setTextColor(startDate: String) {
        val (color, imageResource) = if(TimeFormatter.getEpochMilliFromyyyyDashmmDashddFormat(startDate) < TimeFormatter.getCurrentEpochMilli())
            listOf(R.color.color_8c8c8c, R.drawable.ic_schedule_oval_inactive) else listOf(R.color.color_363636, R.drawable.ic_schedule_oval_active)

        binding.apply {
            listOf(textViewDate, textViewMonth, textViewTitle, textViewTime, textViewLocation).forEach {
                it.setTextColor(root.context.getColor(color))
            }

            listOf(imageViewClock, imageViewLocation).forEach {
                it.setColorFilter(root.context.getColor(color))
            }

            imageViewOval.setImageResource(imageResource)
        }

    }
}