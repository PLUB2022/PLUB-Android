package com.plub.presentation.util

object TimeFormatter {
    fun getAmPmHourMin(hour: Int, min: Int): String {
        return if (hour > 12) {
            "오후 ${hour - 12}시 ${min}분"
        } else "오전 ${hour}시 ${min}분"
    }
}