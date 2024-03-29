package com.plub.presentation.parcelableVo

import android.os.Parcelable
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.notice.NoticeVo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParseNoticeVo(
    val noticeId: Int = -1,
    val title: String = "",
    val content: String = "",
    val createAt: String = "",
    val isHost: Boolean = false,
    val likeCount: Int = -1,
    val commentCount: Int = -1,
    val profileImage: String = "",
    val nickname: String = "",
) : Parcelable {

    companion object : ParseModel<ParseNoticeVo, NoticeVo> {
        override fun mapToDomain(vo: ParseNoticeVo): NoticeVo {
            return vo.run {
                NoticeVo(
                    noticeId = noticeId,
                    title = title,
                    content = content,
                    createAt = createAt,
                    isHost = isHost,
                    likeCount = likeCount,
                    commentCount = commentCount,
                    profileImage = profileImage,
                    nickname = nickname
                )
            }
        }

        override fun mapToParse(vo: NoticeVo): ParseNoticeVo {
            return vo.run {
                ParseNoticeVo(
                    noticeId,
                    title,
                    content,
                    createAt,
                    isHost
                )
            }
        }
    }
}