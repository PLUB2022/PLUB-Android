package com.plub.domain.model.enums

enum class MyGatheringsViewType(val type:Int) {
    PARTICIPATE(0), CREATE(1), MY_GATHERING_CONTENT(2), MY_HOSTING_CONTENT(3);

    companion object {
        fun valueOf(value: Int): MyGatheringsViewType {
            return MyGatheringsViewType.values().find {
                it.type == value
            } ?: MY_GATHERING_CONTENT
        }
    }
}