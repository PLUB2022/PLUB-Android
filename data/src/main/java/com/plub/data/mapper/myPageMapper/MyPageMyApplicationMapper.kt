package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyApplicationResponse
import com.plub.data.dto.myPage.MyGatheringResponse
import com.plub.data.mapper.recruitdetailmapper.host.HostAnswersMapper
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
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
                    position = plubbingInfo.address
                ),
                answerList = answers.map {
                    HostAnswersMapper.mapDtoToModel(it)
                }
            )
        }?: MyPageMyApplicationVo()
    }
}