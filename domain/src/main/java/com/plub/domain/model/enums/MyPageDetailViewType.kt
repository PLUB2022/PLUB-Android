package com.plub.domain.model.enums

enum class MyPageDetailViewType (val type:Int) {
    TOP(0),MY_APPLICANTS_TEXT(1),OTHER_APPLICANTS_TEXT(2),MY_APPLICATION(3),OTHER_APPLICATION(4),EMPTY(5);

    companion object {
        fun valueOf(value: Int): MyPageDetailViewType {
            return MyPageDetailViewType.values().find {
                it.type == value
            } ?: MyPageDetailViewType.EMPTY
        }
    }
}