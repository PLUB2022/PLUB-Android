package com.plub.domain.model.enums

enum class ArchiveItemViewType(val type : Int) {
    EDIT_VIEW(0), IMAGE_TEXT_VIEW(1), IMAGE_VIEW(2), IMAGE_ADD_VIEW(3);

    companion object {
        fun valueOf(value: Int): ArchiveItemViewType {
            return ArchiveItemViewType.values().find {
                it.type == value
            } ?: ArchiveItemViewType.IMAGE_VIEW
        }
    }
}