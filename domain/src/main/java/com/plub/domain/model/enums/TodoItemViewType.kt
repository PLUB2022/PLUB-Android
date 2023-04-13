package com.plub.domain.model.enums

enum class TodoItemViewType(val idx: Int) {
    PLUBING(0), PLANNER(1), DETAIL(2), PROFILE(3);

    companion object {
        fun indexOf(value: Int): TodoItemViewType {
            return TodoItemViewType.values().find {
                it.idx == value
            } ?: PLUBING
        }
    }
}
