package com.plub.domain.model.enums

enum class PlubSearchType(val key: String, val idx: Int) {
    TITLE("title", 0), NAME("name", 1), TITLE_AND_CONTENTS("mix", 2);

    companion object {
        fun valueOf(value: Int): PlubSearchType {
            return PlubSearchType.values().find {
                it.idx == value
            } ?: TITLE
        }
    }
}
