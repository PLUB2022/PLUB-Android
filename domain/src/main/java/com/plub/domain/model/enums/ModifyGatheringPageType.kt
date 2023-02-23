package com.plub.domain.model.enums

enum class ModifyGatheringPageType(val idx: Int) {
    RECRUIT(0),
    INFO(1),
    GUEST(2);

    companion object {
        fun valueOf(value: Int): ModifyGatheringPageType {
            return ModifyGatheringPageType.values().find {
                it.idx == value
            } ?: RECRUIT
        }
    }
}