package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyToDoResponse
import com.plub.data.mapper.todo.TodoTimelineResponseMapper
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo

object MyToDoWithTileMapper : Mapper.ResponseMapper<MyToDoResponse, MyPageToDoWithTitleVo> {
    override fun mapDtoToModel(type: MyToDoResponse?): MyPageToDoWithTitleVo {
        return type?.run {
            MyPageToDoWithTitleVo(
                titleVo = MyPageDetailTitleVo(
                    title = plubbingInfo.name,
                    date = plubbingInfo.days,
                    time = plubbingInfo.time,
                    placeName = plubbingInfo.address,
                    plubbingId = plubbingInfo.plubbingId
                ),
                todoTimelineListVo = TodoTimelineListVo(
                    last = myTodoTimelineResponse.last,
                    content = myTodoTimelineResponse.content.map {
                        TodoTimelineResponseMapper.mapDtoToModel(it)
                    }
                )
            )
        }?: MyPageToDoWithTitleVo()
    }
}