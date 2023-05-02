package com.plub.domain.model.enums

enum class PlubingBoardDetailType(val idx: Int) {
    DETAIL(0), COMMENT(1), COMMENT_REPLY(2), DETAIL_NOTICE(3);

    companion object {
        fun indexOf(value: Int): PlubingBoardDetailType {
            return PlubingBoardDetailType.values().find {
                it.idx == value
            } ?: DETAIL
        }
    }
}
