package com.plub.domain.model.enums

enum class ScheduleCardType(val idx: Int, val type: String) {
    YEAR(0, "YEAR"), CONTENT(1, "CONTENT"), LOADING(2, "LOADING");

    companion object {
        fun indexOf(value: Int): ScheduleCardType {
            return ScheduleCardType.values().find {
                it.idx == value
            } ?: LOADING
        }

        fun typeOf(value: String): ScheduleCardType {
            return ScheduleCardType.values().find {
                it.type == value
            } ?: LOADING
        }
    }
}