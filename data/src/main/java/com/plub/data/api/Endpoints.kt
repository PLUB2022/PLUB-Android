package com.plub.data.api

object Endpoints {

    object FILE {
        const val FILE_URL = "/api/files"
        const val CHANGE_FILE_URL = "$FILE_URL/change"
        const val DELETE_FILE = "$FILE_URL/{type}"
    }

    object AUTH {
        private const val AUTH_URL = "/api/auth"
        const val SOCIAL_LOGIN = "$AUTH_URL/login"
        const val SIGN_UP = "$AUTH_URL/signup"
        const val SOCIAL_LOGIN_ADMIN = "$AUTH_URL/login/admin"
        const val LOGOUT = "$AUTH_URL/logout"
    }

    object ACCOUNT {
        private const val ACCOUNT_URL = "/api/accounts"
        const val NICKNAME_CHECK = "$ACCOUNT_URL/check/nickname/{nickname}"
        const val FETCH_MY_INFO = "$ACCOUNT_URL/me"
        const val FETCH_OTHER_INFO = "$ACCOUNT_URL/{nickname}"
        const val UPDATE_PROFILE = "$FETCH_MY_INFO/profile"
        const val REGIST_INTEREST = "$ACCOUNT_URL/interest"
        const val BROWSE_INTEREST = "$ACCOUNT_URL/interest"
        const val CHANGE_PUSH_NOTIFICATION = FETCH_MY_INFO
    }

    object CATEGORY {
        private const val CATEGORY_URL = "/api/categories"
        const val GET_ALL_CATEGORIES = "$CATEGORY_URL/all"
        const val GET_PARENT_CATEGORIES = "$CATEGORY_URL"
        const val GET_SUB_CATEGORIES = "$CATEGORY_URL/{categoryId}/sub"
    }

    object KAKAO_LOCATION {
        const val KEYWORD_URL = "/v2/local/search/keyword"
    }

    object NOTIFICATIONS {
        private const val NOTIFICATIONS_URL = "/api/notifications"
        private const val NOTIFICATIONS_ID = "$NOTIFICATIONS_URL/{notificationId}"
        const val MY = "$NOTIFICATIONS_URL/accounts/me"
        const val READ = "$NOTIFICATIONS_ID/read"
    }

    object PLUBBING {
        private const val PLUBBING_URL = "/api/plubbings"
        private const val RECRUIT_URL = "$PLUBBING_URL/{plubbingId}/recruit"
        private const val PLUBBING_ID_URL = "$PLUBBING_URL/{plubbingId}"
        private const val ARCHIVE_URL = "$PLUBBING_URL/{plubbingId}/archives"
        private const val ARCHIVE_DETAIL_URL = "$ARCHIVE_URL/{archiveId}"
        const val CREATE_ARCHIVE = ARCHIVE_URL
        const val FETCH_ALL_ARCHIVES = ARCHIVE_URL
        const val FETCH_DETAIL_ARCHIVE = ARCHIVE_DETAIL_URL
        const val EDIT_ARCHIVE = ARCHIVE_DETAIL_URL
        const val DELETE_ARCHIVE = ARCHIVE_DETAIL_URL
        const val CREATE = PLUBBING_URL
        const val RECRUIT = "$PLUBBING_URL/recruit"
        const val BOOKMARK = "$RECRUIT_URL/bookmarks"
        const val BOOKMARK_ME = "$PLUBBING_URL/recruit/bookmarks/me"
        const val FETCH_RECOMMENDATION_GATHERING =  "$PLUBBING_URL/recommendation"
        const val FETCH_CATEGORIES_GATHERING = "$PLUBBING_URL/categories/{categoryId}"
        const val FETCH_DETAIL_RECRUIT = "$RECRUIT_URL"
        const val APPLICANTS_RECRUIT = "$RECRUIT_URL/applicants"
        const val APPROVAL_APPLICANTS = "$APPLICANTS_RECRUIT/{accountId}/approval"
        const val REFUSE_APPLICANTS = "$APPLICANTS_RECRUIT/{accountId}/refuse"
        const val CANCEL_APPLICATION = "$APPLICANTS_RECRUIT/me"
        const val RECRUIT_END = "$RECRUIT_URL/end"
        const val RECRUIT_QUESTIONS = "$RECRUIT_URL/questions"
        const val PLUBING_MAIN = "$PLUBBING_ID_URL/main"
        const val MODIFY_GATHERING_RECRUIT = CANCEL_APPLICATION
        const val KICK_OUT = "$PLUBBING_ID_URL/accounts/{accountId}"

        object MODIFY_GATHERING {
            const val RECRUIT = RECRUIT_URL
        }

        const val MY = "$PLUBBING_URL/my"
        const val CHANGE_STATUS = "$PLUBBING_ID_URL/status"
        const val LEAVE = "$PLUBBING_ID_URL/leave"
        const val MEMBER = "$PLUBBING_ID_URL/members"

        object BOARD {
            const val FEEDS = "$PLUBBING_ID_URL/feeds"
            const val PINS = "$PLUBBING_ID_URL/pins"
            const val FEED_CREATE = "$PLUBBING_ID_URL/feeds"
            const val FEED_CHANGE_PIN = "$PLUBBING_ID_URL/feeds/{feedId}/pin"
            const val FEED_DELETE = "$PLUBBING_ID_URL/feeds/{feedId}"
            const val FEED_DETAIL = "$PLUBBING_ID_URL/feeds/{feedId}"
            const val FEED_EDIT = "$PLUBBING_ID_URL/feeds/{feedId}"
            const val COMMENTS = "$PLUBBING_ID_URL/feeds/{feedId}/comments"
            const val COMMENT_CREATE = "$PLUBBING_ID_URL/feeds/{feedId}/comments"
            const val COMMENT_DELETE = "$PLUBBING_ID_URL/feeds/{feedId}/comments/{commentId}"
            const val COMMENT_EDIT = "$PLUBBING_ID_URL/feeds/{feedId}/comments/{commentId}"
        }

        object SCHEDULE {
            const val CALENDAR = "$PLUBBING_ID_URL/calendar"
            const val CALENDAR_ID = "$CALENDAR/{calendarId}"
            const val ATTEND = "$CALENDAR_ID/attend"
        }

        object TODO {
            const val TIMELINES = "$PLUBBING_ID_URL/timeline"
            const val OTHER_TIMELINES = "$PLUBBING_ID_URL/timeline/accounts/{accountId}"
            const val PROOF = "$PLUBBING_ID_URL/todolist/{todolistId}/proof"
            const val COMPLETE = "$PLUBBING_ID_URL/todolist/{todolistId}/complete"
            const val CANCEL = "$PLUBBING_ID_URL/todolist/{todolistId}/cancel"
            const val TODO_DAYS_IN_MONTH = "$PLUBBING_ID_URL/timeline/year/{year}/month/{month}"
            const val MY_TODO_IN_DAY = "$PLUBBING_ID_URL/timeline/{todoDate}"
            const val TODO_CREATE = "$PLUBBING_ID_URL/todolist"
            const val TODO_EDIT = "$PLUBBING_ID_URL/todolist/{todoId}"
            const val TODO_DELETE = "$PLUBBING_ID_URL/todolist/{todoId}"
            const val TODO_LIKE_TOGGLE = "$PLUBBING_ID_URL/timeline/{timelineId}/like"
            const val TODO_DETAIL = "$PLUBBING_ID_URL/timeline/{timelineId}/todolist"
        }

        object MY_PAGE{
            const val BROWSE_MY_GATHERING = "$PLUBBING_URL/all/my"
            const val BROWSE_MY_APPLICATION = "$PLUBBING_URL/{plubbingId}/recruit/applicants/me"
            const val BROWSE_MY_POST = "$PLUBBING_URL/{plubbingId}/feeds/my"
            const val BROWSE_MY_TODO = "$PLUBBING_URL/{plubbingId}/timeline/my"
        }
    }
}