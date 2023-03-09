package com.plub.domain.model.enums

enum class TodoTimelineViewType(val idx: Int) {
    PLUBING(0), DATE(1);

    companion object {
        fun indexOf(value: Int): TodoTimelineViewType {
            return TodoTimelineViewType.values().find {
                it.idx == value
            } ?: PLUBING
        }
    }
}
