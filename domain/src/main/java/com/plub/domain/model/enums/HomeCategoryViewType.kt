package com.plub.domain.model.enums

enum class HomeCategoryViewType(val type:Int) {
    FIRST_VIEW(0),CATEGORY_VIEW(1);

    companion object {
        fun valueOf(value: Int): HomeCategoryViewType {
            return values().find {
                it.type == value
            } ?: CATEGORY_VIEW
        }
    }
}