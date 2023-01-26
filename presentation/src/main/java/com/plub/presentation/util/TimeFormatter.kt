package com.plub.presentation.util

import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalTime

object TimeFormatter {
    private const val SPLIT_OF_TIME = ":"
    private const val INDEX_OF_HOUR = 0
    private const val INDEX_OF_MIN = 1
    private const val ahmLocaleKorean = "a h시 m분"
    private const val HHmm = "HHmm"

    private val ahmLocaleKoreanFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(ahmLocaleKorean)
    private val HHmmFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(HHmm)

    fun getAmPmHourMin(time:String): String {
        val list = time.split(SPLIT_OF_TIME)
        val hour = list[INDEX_OF_HOUR].toInt()
        val min = list[INDEX_OF_MIN].toInt()
        return ahmLocaleKoreanFormatter.format(LocalTime.of(hour, min))
    }
    fun getAmPmHourMin(hour: Int, min: Int): String {
        return ahmLocaleKoreanFormatter.format(LocalTime.of(hour, min))
    }

    fun getHHmm(hour: Int, min: Int): String {
        return HHmmFormatter.format(LocalTime.of(hour, min))
    }
}