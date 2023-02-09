package com.plub.data.api

object Endpoints {

    object TEST {
        private const val TEST_URL = "/api"
        const val LOGIN_TEST = "$TEST_URL/test"
    }

    object FILE {
        const val FILE_URL = "/api/files"
    }

    object AUTH {
        private const val AUTH_URL = "/api/auth"
        const val SOCIAL_LOGIN = "$AUTH_URL/login"
        const val SIGN_UP = "$AUTH_URL/signup"
        const val SOCIAL_LOGIN_ADMIN = "$AUTH_URL/login/admin"
    }

    object ACCOUNT {
        private const val ACCOUNT_URL = "/api/accounts"
        const val NICKNAME_CHECK = "$ACCOUNT_URL/check/nickname/{nickname}"
        const val FETCH_MY_INFO = "$ACCOUNT_URL/me"
    }

    object CATEGORY {
        private const val CATEGORY_URL = "/api/categories"
        const val GET_ALL_CATEGORIES = "$CATEGORY_URL/all"
    }

    object KAKAO_LOCATION {
        const val KEYWORD_URL = "/v2/local/search/keyword"
    }

    object PLUBBING {
        private const val PLUBBING_URL = "/api/plubbings"
        private const val PLUBBING_ID_URL = "$PLUBBING_URL/{plubbingId}"
        const val CREATE = PLUBBING_URL
        const val RECRUIT = "$PLUBBING_URL/recruit"
        const val BOOKMARK = "$PLUBBING_ID_URL/recruit/bookmarks"
        const val BOOKMARK_ME = "$PLUBBING_URL/recruit/bookmarks/me"
        const val PLUBING_MAIN = "$PLUBBING_ID_URL/main"

        object BOARD {
            const val FEEDS = "$PLUBBING_ID_URL/feeds"
            const val PINS = "$PLUBBING_ID_URL/pins"
        }
    }
}