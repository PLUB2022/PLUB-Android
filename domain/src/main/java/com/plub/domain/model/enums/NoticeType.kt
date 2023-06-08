package com.plub.domain.model.enums

enum class NoticeType(val type : Int) {
    APP(0), PLUBING(1), LOADING(2);

    companion object {
        fun valueOf(value: Int): NoticeType {
            return NoticeType.values().find {
                it.type == value
            } ?: NoticeType.PLUBING
        }
    }
}