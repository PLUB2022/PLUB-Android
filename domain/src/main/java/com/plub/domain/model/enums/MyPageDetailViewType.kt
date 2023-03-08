package com.plub.domain.model.enums

enum class MyPageDetailViewType (val type:Int) {
    TOP(0),MY_APPLICANTS(1),OTHER_APPLICANTS(2),APPLICANTS(3),EMPTY(4);

    companion object {
        fun valueOf(value: Int): MyPageDetailViewType {
            return MyPageDetailViewType.values().find {
                it.type == value
            } ?: MyPageDetailViewType.EMPTY
        }
    }
}