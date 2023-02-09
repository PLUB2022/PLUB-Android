package com.plub.domain.model.enums

enum class PlubingFeedType(val type: String) {
    TEXT("LINE"), IMAGE("PHOTO"), TEXT_AND_IMAGE("PHOTO_LINE");

    companion object {
        fun typeOf(value: String): PlubingFeedType {
            return PlubingFeedType.values().find {
                it.type == value
            } ?: TEXT
        }
    }
}
