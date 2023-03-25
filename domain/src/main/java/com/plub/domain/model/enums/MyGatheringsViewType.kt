package com.plub.domain.model.enums

enum class MyGatheringsViewType(val type:Int) {
    PARTICIPATE(0), HOSTING(1), CONTENT(2);

    companion object {
        fun valueOf(value: Int): MyGatheringsViewType {
            return MyGatheringsViewType.values().find {
                it.type == value
            } ?: CONTENT
        }
    }
}