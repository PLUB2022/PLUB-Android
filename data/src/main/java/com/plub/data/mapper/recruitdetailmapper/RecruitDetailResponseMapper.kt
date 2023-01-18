package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recruitdetail.RecruitDetailResponse
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo

object RecruitDetailResponseMapper: Mapper.ResponseMapper<RecruitDetailResponse, RecruitDetailResponseVo> {
    override fun mapDtoToModel(type: RecruitDetailResponse?): RecruitDetailResponseVo {
        return type?.run {
            RecruitDetailResponseVo(
                title,
                introduce,
                categories,
                name,
                goal,
                days,
                address,
                roadAddress,
                placeName,
                placePositionX,
                placePositionY,
                remainAccountNum,
                time,
                isBookmarked,
                isApplied, curAccountNum,
                joinedAccounts.map {
                    RecruitDetailJoinedAccountsMapper.mapDtoToModel(it)
                }
            )
        }?: RecruitDetailResponseVo("", "", emptyList(), "", "", emptyList(), ""
        , "", "", 0.0, 0.0, 0, "", false, false, 0, emptyList()
        )
    }
}