package com.plub.domain.model.enums

enum class PlubingMainPageType(val idx: Int) {
    BOARD(0), TODO_LIST(1);

    companion object {
        fun valueOf(value: Int): PlubingMainPageType {
            return PlubingMainPageType.values().find {
                it.idx == value
            } ?: BOARD
        }
    }
}
