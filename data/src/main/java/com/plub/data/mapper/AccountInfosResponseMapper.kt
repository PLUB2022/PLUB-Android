package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.account.AccountInfoResponse
import com.plub.data.dto.account.AccountInfosResponse
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.data.dto.todo.TodoTimelineResponse
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

object AccountInfosResponseMapper : Mapper.ResponseMapper<AccountInfosResponse, List<AccountInfoVo>> {
    override fun mapDtoToModel(type: AccountInfosResponse?): List<AccountInfoVo> {
        return type?.run {
            accountInfo.map {
                AccountInfoResponseMapper.mapDtoToModel(it)
            }
        } ?: emptyList()
    }
}