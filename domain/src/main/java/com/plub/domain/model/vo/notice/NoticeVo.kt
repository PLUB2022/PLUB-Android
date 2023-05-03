package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.NoticeType

data class NoticeVo(
    val noticeType: NoticeType = NoticeType.PLUBING,
    val noticeId: Int = -1,
    val title: String = "",
    val content: String = "",
    val createAt: String = "",
    val likeCount: Int = -1,
    val commentCount: Int = -1,
    val isHost: Boolean = false,
    val profileImage: String = "",
    val nickname: String = "",
) : DomainModel