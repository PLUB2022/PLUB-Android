package com.plub.domain.model.enums

enum class SignUpPageType(val idx: Int) {
    TERMS(0),AUTHENTICATION(1), PERSONAL_INFO(2), PROFILE(3), MORE_INFO(4), HOBBY(5);

    companion object {
        fun valueOf(value: Int): SignUpPageType {
            return values().find {
                it.idx == value
            } ?: TERMS
        }
    }
}