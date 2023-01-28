package com.plub.domain.model.enums

enum class MainHasDataType(val type:Int) {
    NO_DATA(0),DATA(1), FIRST(2);

    companion object {
        fun valueOf(value: Int): MainHasDataType {
            return values().find {
                it.type == value
            } ?: DATA
        }
    }
}