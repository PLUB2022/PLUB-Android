package com.plub.domain.model.enums

enum class PlubingBoardWriteType(val type: String) {
    CREATE("CREATE"), EDIT("EDIT");

    companion object {
        fun typeOf(value: String): PlubingBoardWriteType {
            return PlubingBoardWriteType.values().find {
                it.type == value
            } ?: CREATE
        }
    }
}
