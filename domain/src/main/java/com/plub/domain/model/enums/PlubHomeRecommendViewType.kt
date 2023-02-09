package com.plub.domain.model.enums

enum class PlubHomeRecommendViewType(val type:Int) {
    REGISTER_HOBBIES_VIEW(0),RECOMMEND_VIEW(1), FIRST_VIEW(2), LOADING(3);

    companion object {
        fun valueOf(value: Int): PlubHomeRecommendViewType {
            return values().find {
                it.type == value
            } ?: RECOMMEND_VIEW
        }
    }
}