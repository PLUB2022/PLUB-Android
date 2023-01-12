package com.plub.data.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    private val signUpFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    fun getSignUpBirthday(birthday:Calendar):String {
        return signUpFormat.format(birthday.time)
    }
}