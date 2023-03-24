package com.plub.data.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    private val signUpFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private const val SPLIT_OF_DATE = "-"
    private const val INDEX_OF_YEAR = 0

    fun getSignUpBirthday(birthday:Calendar):String {
        return signUpFormat.format(birthday.time)
    }

    fun getIntYearFromyyyyDashmmDashddFormat(yyyyDashmmDashddFormat: String): Int {
        return try {
            yyyyDashmmDashddFormat.split(SPLIT_OF_DATE)[INDEX_OF_YEAR].toInt()
        } catch (e: Exception) {
            0
        }
    }
}