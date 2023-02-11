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
        private const val ARCHIVE_URL = "$PLUBBING_URL/{plubbingId}/archives"
        private const val ARCHIVE_DETAIL_URL = "$ARCHIVE_URL/{archiveId}"
        const val CREATE_ARCHIVE = ARCHIVE_URL
        const val FETCH_ALL_ARCHIVES = ARCHIVE_URL
        const val FETCH_DETAIL_ARCHIVE = ARCHIVE_DETAIL_URL
        const val EDIT_ARCHIVE = ARCHIVE_DETAIL_URL
        const val DELETE_ARCHIVE = ARCHIVE_DETAIL_URL
        const val CREATE = PLUBBING_URL
        const val RECRUIT = "$PLUBBING_URL/recruit"
        const val BOOKMARK = "$PLUBBING_URL/{plubbingId}/recruit/bookmarks"
        const val BOOKMARK_ME = "$PLUBBING_URL/recruit/bookmarks/me"
    }
}