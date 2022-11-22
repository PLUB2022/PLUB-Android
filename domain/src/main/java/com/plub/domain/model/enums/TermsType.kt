package com.plub.domain.model.enums

enum class TermsType(val viewType: Int) {
    ALL(1),
    PRIVACY(2),
    LOCATION(3),
    AGE(4),
    COLLECT(5),
    MARKETING(6),
    SERVICE(7);

    companion object {
        fun valueOf(value: Int): TermsType {
            return values().find {
                it.viewType == value
            } ?: ALL
        }
    }
}