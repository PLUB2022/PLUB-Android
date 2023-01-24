package com.plub.presentation.util

import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalTime

object TimeFormatter {
    const val ahmLocaleKorean = "a h시 m분"
    const val HHmm = "HHmm"

    private val ahmLocaleKoreanFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(ahmLocaleKorean)
    private val HHmmFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(HHmm)

    fun getAmPmHourMin(hour: Int, min: Int): String {
        return ahmLocaleKoreanFormatter.format(LocalTime.of(hour, min))
    }

    fun getHHmm(hour: Int, min: Int): String {
        return HHmmFormatter.format(LocalTime.of(hour, min))
    }
}