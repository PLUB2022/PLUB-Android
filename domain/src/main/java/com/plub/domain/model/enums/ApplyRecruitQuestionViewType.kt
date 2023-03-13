package com.plub.domain.model.enums

enum class ApplyRecruitQuestionViewType(val type:Int) {
    FIRST(0),QUESTION(1);

    companion object {
        fun valueOf(value: Int): ApplyRecruitQuestionViewType {
            return ApplyRecruitQuestionViewType.values().find {
                it.type == value
            } ?: QUESTION
        }
    }
}