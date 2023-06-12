package com.plub.domain.model.enums

enum class ArchiveViewType(val type: Int) {
    ARCHIVE(0), LOADING(1);

    companion object {
        fun valueOf(value: Int): ArchiveViewType {
            return ArchiveViewType.values().find {
                it.type == value
            } ?: ARCHIVE
        }
    }
}
