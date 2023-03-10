package com.plub.domain.model.enums

enum class MyPageGatheringStateType(val type:String) {
    ACTIVE("ACTIVE"), END("END"), DELETED("DELETED"), PAUSE("PAUSE");

    companion object {
        fun valueOf(type: String): MyPageGatheringStateType {
            return MyPageGatheringStateType.values().find {
                it.type == type
            } ?: MyPageGatheringStateType.ACTIVE
        }
    }
}