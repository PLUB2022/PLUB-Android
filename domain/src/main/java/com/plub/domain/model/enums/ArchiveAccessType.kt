package com.plub.domain.model.enums

enum class ArchiveAccessType(val type : String) {
    HOST("host"), NORMAL("normal"), AUTHOR("author");

    companion object {
        fun valuesOf(type: String): ArchiveAccessType {
            return ArchiveAccessType.values().find {
                it.type == type
            } ?: ArchiveAccessType.NORMAL
        }
    }
}