package com.plub.presentation.ui.main.home.profile.recruiting

import com.plub.presentation.ui.Event


sealed class MyPageRecruitingGatheringEvent : Event {
    object GoToRecruit : MyPageRecruitingGatheringEvent()
}
