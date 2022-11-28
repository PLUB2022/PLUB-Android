package com.plub.domain.model.enums

enum class SignUpPageType(val idx: Int) {
    TERMS(0), PERSONAL_INFO(1), PROFILE(2), MORE_INFO(3), HOBBY(4);

    companion object {
        fun valueOf(value: Int): SignUpPageType {
            return values().find {
                it.idx == value
            } ?: TERMS
        }
    }
}