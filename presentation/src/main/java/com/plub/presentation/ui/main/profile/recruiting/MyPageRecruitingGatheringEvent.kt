package com.plub.presentation.ui.main.profile.recruiting

import com.plub.presentation.ui.Event


sealed class MyPageRecruitingGatheringEvent : Event {
    object GoToRecruit : MyPageRecruitingGatheringEvent()
    data class ShowApproveDialog(val accountId : Int) : MyPageRecruitingGatheringEvent()
    data class ShowRefuseDialog(val accountId : Int) : MyPageRecruitingGatheringEvent()
}
