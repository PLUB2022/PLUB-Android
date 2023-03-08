package com.plub.presentation.ui.main.plubing.schedule.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ScheduleCardType
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleContentBinding
import com.plub.presentation.util.TimeFormatter

class PlubingScheduleContentViewHolder(
    private val binding: LayoutRecyclerPlubingScheduleContentBinding,
    private val onClick: (() -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick?.invoke()
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
        }
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

}