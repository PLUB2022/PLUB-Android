package com.plub.data.api

object Endpoints {
    private const val BASE_URL = "www.plub.co.kr"

    object AUTH {
        private const val AUTH_URL = "$BASE_URL/auth"
        const val SOCIAL_LOGIN = "$AUTH_URL/login"
    }
}