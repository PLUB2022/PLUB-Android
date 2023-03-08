package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.domain.model.vo.board.BoardCommentListVo

object BoardCommentListResponseMapper : Mapper.ResponseMapper<BoardCommentListResponse, BoardCommentListVo> {
    override fun mapDtoToModel(type: BoardCommentListResponse?): BoardCommentListVo {
        return type?.run {
            BoardCommentListVo(
                totalPages = totalPages,
                totalElements = totalElements,
                content = content.map {
                    BoardCommentResponseMapper.mapDtoToModel(it)
                },
                last = last
            )
        }?: BoardCommentListVo()
    }
}