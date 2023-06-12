package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyBoardResponse
import com.plub.data.mapper.PlubingBoardResponseMapper
import com.plub.domain.model.vo.board.PlubingBoardListVo

object MyBoardMapper : Mapper.ResponseMapper<MyBoardResponse, PlubingBoardListVo> {
    override fun mapDtoToModel(type: MyBoardResponse?): PlubingBoardListVo {
        return type?.run {
            PlubingBoardListVo(
                totalPages = myFeedList.totalPages,
                totalElements = myFeedList.totalElements,
                last = myFeedList.last,
                content = myFeedList.content.map {
                    PlubingBoardResponseMapper.mapDtoToModel(it)
                }
            )
        }?: PlubingBoardListVo()
    }
}