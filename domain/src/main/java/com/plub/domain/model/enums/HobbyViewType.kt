package com.plub.domain.model.enums

enum class HobbyViewType(val type:Int) {
    HOBBY(0),LATE_PICK(1), FIRST_TOPIC(2);

    companion object {
        fun valueOf(value: Int): HobbyViewType {
            return values().find {
                it.type == value
            } ?: HOBBY
        }
    }
}