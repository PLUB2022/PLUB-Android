package com.plub.domain.model.enums

enum class ArchiveMenuType(val type : Int) {
    EDIT(0), REPORT(1), DELETE(2);

    companion object {
        fun valueOf(type: Int): ArchiveMenuType {
            return ArchiveMenuType.values().find {
                it.type == type
            } ?: ArchiveMenuType.REPORT
        }
    }
}