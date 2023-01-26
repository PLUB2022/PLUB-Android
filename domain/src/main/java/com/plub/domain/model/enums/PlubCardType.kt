package com.plub.domain.model.enums

enum class PlubCardType(val type:Int, val spanSize:Int) {
    LIST(0, 2), GRID(1, 1);

    companion object {
        const val TOTAL_SPAN_SIZE = 2

        fun valueOf(value: Int): PlubCardType {
            return PlubCardType.values().find {
                it.type == value
            } ?: LIST
        }
    }
}
