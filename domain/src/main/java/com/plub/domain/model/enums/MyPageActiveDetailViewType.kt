package com.plub.domain.model.enums

enum class MyPageActiveDetailViewType (val type:Int) {
    TOP(0),MY_TODO(1),MY_POST(2);

    companion object {
        fun valueOf(type: Int): MyPageActiveDetailViewType {
            return MyPageActiveDetailViewType.values().find {
                it.type == type
            } ?: MyPageActiveDetailViewType.TOP
        }
    }
}