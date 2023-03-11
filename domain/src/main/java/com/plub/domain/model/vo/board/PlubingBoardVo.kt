package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType

data class PlubingBoardVo(
    val viewType: PlubingBoardType = PlubingBoardType.NORMAL,
    val feedType: PlubingFeedType = PlubingFeedType.TEXT,
    val feedId: Int = -1,
    val title: String = "",
    val feedImage: String = "",
    val content: String = "",
    val createAt: String = "",
    val pin: Boolean = false,
    val profileImage: String? = "",
    val nickname: String = "",
    val likeCount: Int = -1,
    val commentCount: Int = -1,
    val isAuthor:Boolean = false,
    val isHost:Boolean = false
) : DomainModel