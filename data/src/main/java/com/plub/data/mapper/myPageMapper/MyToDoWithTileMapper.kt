package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyApplicationResponse
import com.plub.data.dto.myPage.MyToDoResponse
import com.plub.data.dto.todo.MyTodoTimelineResponse
import com.plub.data.mapper.recruitDetailMapper.host.HostAnswersMapper
import com.plub.data.mapper.todo.TodoTimelineListResponseMapper
import com.plub.data.mapper.todo.TodoTimelineResponseMapper
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
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
                    position = plubbingInfo.address
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