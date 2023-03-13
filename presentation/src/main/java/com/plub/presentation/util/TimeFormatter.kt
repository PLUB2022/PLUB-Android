package com.plub.presentation.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneOffset

object TimeFormatter {
    private const val SPLIT_OF_TIME = ":"
    private const val SPLIT_OF_DATE = "-"
    private const val INDEX_OF_HOUR = 0
    private const val INDEX_OF_MIN = 1
    private const val INDEX_OF_YEAR = 0
    private const val INDEX_OF_MONTH = 1
    private const val INDEX_OF_DAY = 2
    private const val ahmLocaleKorean = "a h시 m분"
    private const val ah_colon_mm = "a h:mm"
    private const val HHcolonmm = "HH:mm"
    private const val HHmm = "HH:mm"
    private const val yyyydotMMdotddE = "yyyy. MM. dd E"
    private const val yyyydashMMdashdd = "yyyy-MM-dd"

    private val ahmLocaleKoreanFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(ahmLocaleKorean)
    private val ah_colon_mmFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(ah_colon_mm)
    private val HHmmFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(HHmm)
    private val HHcolonmmFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(HHcolonmm)
    private val yyyydotMMdotddEFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(yyyydotMMdotddE)
    private val yyyydashMMdashddFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(yyyydashMMdashdd)

    fun getyyyydashMMdashdd(year: Int, month: Int, day: Int): String {
        return yyyydashMMdashddFormatter.format(LocalDate.of(year, month, day))
    }

    fun get_ah_colon_mm(time: String): String {
        val list = time.split(SPLIT_OF_TIME)
        val hour = list[INDEX_OF_HOUR].toInt()
        val min = list[INDEX_OF_MIN].toInt()
        return ah_colon_mmFormatter.format(LocalTime.of(hour, min))
    }

    fun getAmPmHourMin(time:String): String {
        val list = time.split(SPLIT_OF_TIME)
        val hour = list[INDEX_OF_HOUR].toInt()
        val min = list[INDEX_OF_MIN].toInt()
        return ahmLocaleKoreanFormatter.format(LocalTime.of(hour, min))
    }
    fun getAmPmHourMin(hour: Int, min: Int): String {
        return ahmLocaleKoreanFormatter.format(LocalTime.of(hour, min))
    }

    fun getAmPmHourColonMin(hour: Int, min: Int): String {
        return ah_colon_mmFormatter.format(LocalTime.of(hour, min))
    }

    fun getHHmm(hour: Int, min: Int): String {
        return HHmmFormatter.format(LocalTime.of(hour, min))
    }

    fun getHHcolonmm(hour: Int, min: Int): String {
        return HHcolonmmFormatter.format(LocalTime.of(hour, min))
    }

    fun getyyyydotMMdotddE(year: Int, month: Int, day: Int): String {
        return yyyydotMMdotddEFormatter.format(LocalDate.of(year, month, day))
    }

    fun getIntYearFromyyyyDashmmDashddFormat(yyyyDashmmDashddFormat: String): Int {
        return try {
            yyyyDashmmDashddFormat.split(SPLIT_OF_DATE)[INDEX_OF_YEAR].toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun getIntMonthFromyyyyDashmmDashddFormat(yyyyDashmmDashddFormat: String): Int {
        return try {
            yyyyDashmmDashddFormat.split(SPLIT_OF_DATE)[INDEX_OF_MONTH].toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun getStringDayFromyyyyDashmmDashddFormat(yyyyDashmmDashddFormat: String): String {
        return try {
            yyyyDashmmDashddFormat.split(SPLIT_OF_DATE)[INDEX_OF_DAY]
        } catch (e: Exception) {
            ""
        }
    }

    fun getEpochMilliFromyyyyDashmmDashddFormat(yyyyDashmmDashddFormat: String): Long {
        val (year, month, day) = yyyyDashmmDashddFormat.split(SPLIT_OF_DATE).map { it.toIntOrNull() ?: 0 }
        return LocalDate.of(year, month, day).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    fun getCurrentEpochMilli(): Long {
        return LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }
}