package com.plub.domain.model.enums

enum class PlubingBoardType(val idx: Int, val type: String) {
    NORMAL(0, "NORMAL"), PIN(1, "PIN"), SYSTEM(2, "SYSTEM"), CLIP_BOARD(3, "CLIP_BOARD");

    companion object {
        fun indexOf(value: Int): PlubingBoardType {
            return PlubingBoardType.values().find {
                it.idx == value
            } ?: NORMAL
        }

        fun typeOf(value: String): PlubingBoardType {
            return PlubingBoardType.values().find {
                it.type == value
            } ?: NORMAL
        }
    }
}
