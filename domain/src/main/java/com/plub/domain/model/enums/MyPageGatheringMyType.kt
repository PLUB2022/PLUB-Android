package com.plub.domain.model.enums

enum class MyPageGatheringMyType(val type:String) {
    HOST("HOST"), GUEST("GUEST"), END("END");

    companion object {
        fun valueOf(type: String): MyPageGatheringMyType {
            return MyPageGatheringMyType.values().find {
                it.type == type
            } ?: MyPageGatheringMyType.HOST
        }
    }
}