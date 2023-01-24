package com.plub.domain.model.enums

enum class PlubSortType(val key: String) {
    POPULAR("popular"), NEW("new");

    companion object {
        fun fromKey(value: String): PlubSortType {
            return PlubSortType.values().find {
                it.key == value
            } ?: POPULAR
        }
    }
}
