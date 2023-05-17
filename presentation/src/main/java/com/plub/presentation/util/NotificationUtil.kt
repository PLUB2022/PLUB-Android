package com.plub.presentation.util

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.NotificationType
import com.plub.domain.model.enums.NotificationType.*
import com.plub.presentation.R
import com.plub.presentation.ui.main.home.recruitment.RecruitmentFragmentArgs
import com.plub.presentation.ui.main.plubing.PlubingMainFragmentArgs
import com.plub.presentation.ui.main.plubing.board.detail.BoardDetailFragmentArgs
import com.plub.presentation.ui.main.plubing.notice.NoticeFragmentArgs
import com.plub.presentation.ui.main.plubing.schedule.PlubingScheduleFragmentArgs
import com.plub.presentation.ui.main.profile.recruiting.RecruitingGatheringFragment
import com.plub.presentation.ui.main.profile.recruiting.RecruitingGatheringFragmentArgs
import kotlinx.parcelize.Parcelize

object NotificationUtil {

    fun getBundleAndDestination(type: String, targetId: Int): NavigationBundle {
        return try {
            when(valueOf(type)) {
                REPORTED_ONCE, BAN_ONE_MONTH, BAN_PERMANENTLY -> NavigationBundle()

                KICK_MEMBER, UNBAN -> NavigationBundle(destination = R.id.menu_navigation_gathering)

                APPLY_RECRUIT -> NavigationBundle(RecruitingGatheringFragmentArgs(targetId).toBundle(), R.id.myPageRecruitingGatheringFragment)

                LEAVE_PLUBBING, APPROVE_RECRUIT -> NavigationBundle(PlubingMainFragmentArgs(targetId).toBundle(), R.id.plubingMainFragment)

                CREATE_FEED_COMMENT, CREATE_FEED_COMMENT_COMMENT, PINNED_FEED -> NavigationBundle(BoardDetailFragmentArgs(targetId).toBundle(), R.id.plubingBoardDetailFragment)

                CREATE_NOTICE -> NavigationBundle(NoticeFragmentArgs(NoticeType.PLUBING).toBundle(), R.id.noticeFragment)

                CREATE_UPDATE_CALENDAR -> NavigationBundle(PlubingScheduleFragmentArgs(targetId).toBundle(), R.id.plubingScheduleFragment)
            }
        } catch (_: IllegalArgumentException) {
            NavigationBundle()
        }

    }
}

@Parcelize
data class NavigationBundle(
    val bundle: Bundle = bundleOf(),
    val destination: Int = R.id.menu_navigation_main
) : Parcelable