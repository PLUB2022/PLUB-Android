package com.plub.domain.model.enums

enum class HomeViewType(val type:Int) {
    TITLE_VIEW(0), CATEGORY_VIEW(1), RECOMMEND_TITLE_VIEW(2), REGISTER_HOBBIES_VIEW(3), RECOMMEND_GATHERING_VIEW(4), EMPTY(5);

    companion object {
        fun valueOf(value: Int): HomeViewType {
            return HomeViewType.values().find {
                it.type == value
            } ?: HomeViewType.TITLE_VIEW
        }
    }
}