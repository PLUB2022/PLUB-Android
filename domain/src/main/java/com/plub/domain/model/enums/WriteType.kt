package com.plub.domain.model.enums

enum class WriteType(val type: String) {
    CREATE("CREATE"), EDIT("EDIT");

    companion object {
        fun typeOf(value: String): WriteType {
            return WriteType.values().find {
                it.type == value
            } ?: CREATE
        }
    }
}
