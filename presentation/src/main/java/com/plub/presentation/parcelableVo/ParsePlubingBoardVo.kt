package com.plub.presentation.parcelableVo

import android.os.Parcelable
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParsePlubingBoardVo(
    val viewType: PlubingBoardType = PlubingBoardType.NORMAL,
    val feedType: PlubingFeedType = PlubingFeedType.TEXT,
    val feedId: Int = -1,
    val title: String = "",
    val feedImage: String = "",
    val content: String = "",
    val createAt: String = "",
    val pin: Boolean = false,
    val profileImage: String = "",
    val nickname: String = "",
    val likeCount: Int = -1,
    val commentCount: Int = -1,
    val isAuthor: Boolean = false,
    val isHost: Boolean = false
) : Parcelable {

    companion object : ParseModel<ParsePlubingBoardVo, PlubingBoardVo> {
        override fun mapToDomain(vo: ParsePlubingBoardVo): PlubingBoardVo {
            return vo.run {
                PlubingBoardVo(
                    viewType,
                    feedType,
                    feedId,
                    title,
                    feedImage,
                    content,
                    createAt,
                    pin,
                    profileImage,
                    nickname,
                    likeCount,
                    commentCount,
                    isAuthor,
                    isHost
                )
            }
        }

        override fun mapToParse(vo: PlubingBoardVo): ParsePlubingBoardVo {
            return vo.run {
                ParsePlubingBoardVo(
                    viewType,
                    feedType,
                    feedId,
                    title,
                    feedImage,
                    content,
                    createAt,
                    pin,
                    profileImage,
                    nickname,
                    likeCount,
                    commentCount,
                    isAuthor,
                    isHost
                )
            }
        }
    }
}