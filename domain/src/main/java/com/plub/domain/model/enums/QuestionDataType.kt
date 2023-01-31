package com.plub.domain.model.enums

enum class QuestionDataType(val type:Int) {
    FIRST(0),DATA(1);

    companion object {
        fun valueOf(value: Int): QuestionDataType {
            return QuestionDataType.values().find {
                it.type == value
            } ?: DATA
        }
    }
}