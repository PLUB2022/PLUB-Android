package com.plub.domain.util

import java.util.regex.Pattern

object RegexUtil {
    private const val NON_SPECIAL_CHARACTER = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\\s]*$"
    private const val BLANK_CHARACTER = "(.*)\\s(.*)"

    fun hasBlackCharacter(str:String):Boolean = Pattern.matches(BLANK_CHARACTER, str)
    fun hasSpecialCharacter(str:String):Boolean = !Pattern.matches(NON_SPECIAL_CHARACTER, str)
}