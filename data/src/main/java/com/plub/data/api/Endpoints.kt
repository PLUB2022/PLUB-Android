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
        const val REGIST_INTEREST = "$ACCOUNT_URL/interest"
        const val BROWSE_INTEREST = "$ACCOUNT_URL/me/interest"
    }

    object CATEGORY {
        private const val CATEGORY_URL = "/api/categories"
        const val GET_ALL_CATEGORIES = "$CATEGORY_URL/all"
        const val GET_BIG_CATEGORIES = "$CATEGORY_URL"
    }

    object KAKAO_LOCATION {
        const val KEYWORD_URL = "/v2/local/search/keyword"
    }

    object PLUBBING {
        private const val PLUBBING_URL = "/api/plubbings"
        const val CREATE = PLUBBING_URL
        const val RECRUIT = "$PLUBBING_URL/recruit"
        const val BOOKMARK = "$PLUBBING_URL/{plubbingId}/recruit/bookmarks"
        const val BOOKMARK_ME = "$PLUBBING_URL/recruit/bookmarks/me"
        const val FETCH_RECOMMENDATION_GATHERING =  "$PLUBBING_URL/recommendation"
        const val FETCH_CATEGORIES_GATHERING = "$PLUBBING_URL/categories/{categoryId}"
        const val FETCH_DETAIL_RECRUIT = "$PLUBBING_URL/{plubbingId}/recruit"
    }

    object RECRUIT {
        private const val PLUBBING_URL = "/api/plubbings"
        private const val RECRUIT_URL = "$PLUBBING_URL/{plubbingId}/recruit"
        const val APPLICANTS_RECRUIT = "$RECRUIT_URL/applicants"
        const val APPROVAL_APPLICANTS = "$APPLICANTS_RECRUIT/{accountId}/approval"
        const val REFUSE_APPLICANTS = "$APPLICANTS_RECRUIT/{accountId}/refuse"
        const val BOOKMARK_RECRUIT = "$RECRUIT_URL/bookmarks"
        const val RECRUIT_END = "$RECRUIT_URL/end"
        const val RECRUIT_QUESTIONS = "$RECRUIT_URL/questions"
    }
}