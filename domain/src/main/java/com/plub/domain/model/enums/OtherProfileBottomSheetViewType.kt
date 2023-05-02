package com.plub.domain.model.enums

enum class OtherProfileBottomSheetViewType(val type:Int) {
    PROFILE(0),TODO(1);

    companion object {
        fun valueOf(type: Int): OtherProfileBottomSheetViewType {
            return OtherProfileBottomSheetViewType.values().find {
                it.type == type
            } ?: OtherProfileBottomSheetViewType.PROFILE
        }
    }
}