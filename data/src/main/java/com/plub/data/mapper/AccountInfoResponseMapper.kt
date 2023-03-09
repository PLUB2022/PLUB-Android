package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.account.AccountInfoResponse
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.data.dto.todo.TodoTimelineResponse
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

object AccountInfoResponseMapper : Mapper.ResponseMapper<AccountInfoResponse, AccountInfoVo> {
    override fun mapDtoToModel(type: AccountInfoResponse?): AccountInfoVo {
        return type?.run {
            AccountInfoVo(
                userId = accountId,
                nickname = nickname,
                profileImage = profileImage ?: ""
            )
        } ?: AccountInfoVo()
    }
}