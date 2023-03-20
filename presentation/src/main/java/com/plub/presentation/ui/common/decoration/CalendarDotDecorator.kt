package com.plub.presentation.ui.common.decoration

import com.plub.presentation.R
import com.plub.presentation.util.px
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class CalendarDotDecorator : DayViewDecorator {

    companion object {
        private const val DOT_SIZE = 2f
    }

    private var days:List<Int> = emptyList()
    private val dotColor = R.color.color_5f5ff9

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(DOT_SIZE.px, dotColor))
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return days.contains(day.day)
    }

    fun setDays(list: List<Int>) {
        days = list
    }
}