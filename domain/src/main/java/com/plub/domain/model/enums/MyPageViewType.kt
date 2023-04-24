package com.plub.domain.model.enums

enum class MyPageViewType(val type:Int) {
    PROFILE(0),GATHERING(1), EMPTY(2);

    companion object {
        fun valueOf(type: Int): MyPageViewType {
            return MyPageViewType.values().find {
                it.type == type
            } ?: MyPageViewType.PROFILE
        }
    }
}