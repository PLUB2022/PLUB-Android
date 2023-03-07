package com.plub.domain.model.enums

enum class PlubingCommentType(val type: String) {
    COMMENT("COMMENT"), REPLY("REPLY");

    companion object {
        fun typeOf(value: String): PlubingCommentType {
            return PlubingCommentType.values().find {
                it.type == value
            } ?: COMMENT
        }
    }
}
