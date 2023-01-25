package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.RecruitDetailJoinedAccountsList
import com.plub.data.dto.recruitdetail.RecruitDetailResponse
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo

object RecruitDetailResponseMapper: Mapper.ResponseMapper<RecruitDetailResponse, RecruitDetailResponseVo> {
    override fun mapDtoToModel(type: RecruitDetailResponse?): RecruitDetailResponseVo {
        return type?.run {
            RecruitDetailResponseVo(
                recruitTitle,
                recruitIntroduce,
                categories,
                plubbingName,
                plubbingGoal,
                plubbingMainImage,
                plubbingDays,
                plubbingTime,
                isBookmarked,
                isApplied,
                curAccountNum,
                joinedAccounts.map {
                    RecruitDetailJoinedAccountsMapper.mapDtoToModel(it)
                }
            )
        }?: RecruitDetailResponseVo("", "", emptyList(),"","","", emptyList(),"",
            false,false,0, emptyList()
        )
    }
}