package com.plub.presentation.ui.common.decoration

import android.graphics.drawable.Drawable
import com.plub.presentation.R
import com.plub.presentation.util.px
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class CalendarTodayDecorator(private val backgroundDrawable: Drawable?) : DayViewDecorator {

    override fun decorate(view: DayViewFacade) {
        backgroundDrawable?.let { view.setBackgroundDrawable(it) }
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return CalendarDay.today() == day
    }
}