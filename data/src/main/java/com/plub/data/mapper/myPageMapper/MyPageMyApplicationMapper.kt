package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyApplicationResponse
import com.plub.data.mapper.recruitDetailMapper.host.HostAnswersMapper
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo

object MyPageMyApplicationMapper : Mapper.ResponseMapper<MyApplicationResponse, MyPageMyApplicationVo> {
    override fun mapDtoToModel(type: MyApplicationResponse?): MyPageMyApplicationVo {
        return type?.run {
            MyPageMyApplicationVo(
                recruitDate = recruitDate,
                titleVo = MyPageDetailTitleVo(
                    title = plubbingInfo.name,
                    date = plubbingInfo.days,
                    time = plubbingInfo.time,
                    position = plubbingInfo.address,
                    plubbingId = plubbingInfo.plubbingId
                ),
                answerList = answers.map {
                    HostAnswersMapper.mapDtoToModel(it)
                }
            )
        }?: MyPageMyApplicationVo()
    }
}