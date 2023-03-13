package com.plub.domain.model.enums

enum class DialogCheckboxItemType(val value: String = "", val kor: String = "") {
    ALARM_NONE(
        value = "NONE",
        kor = "알림 없음"
    ),
    ALARM_FIVE_MINUTES(
        value = "FIVE_MINUTES",
        kor = "5분 전"
    ),
    ALARM_FIFTEEN_MINUTES(
        value = "FIFTEEN_MINUTES",
        kor = "15분 전"
    ),
    ALARM_THIRTY_MINUTES(
        value = "THIRTY_MINUTES",
        kor = "30분 전"
    ),
    ALARM_ONE_HOUR(
        value = "ONE_HOUR",
        kor = "1시간 전"
    ),
    ALARM_ONE_DAY(
        value = "ONE_DAY",
        kor = "1일 전"
    ),
    ALARM_ONE_WEEK(
        value = "ONE_WEEK",
        kor = "1주 전"
    )
}