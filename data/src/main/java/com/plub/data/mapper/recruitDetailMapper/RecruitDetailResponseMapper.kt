package com.plub.data.mapper.recruitDetailMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitDetail.RecruitDetailResponse
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo

object RecruitDetailResponseMapper: Mapper.ResponseMapper<RecruitDetailResponse, RecruitDetailResponseVo> {
    override fun mapDtoToModel(type: RecruitDetailResponse?): RecruitDetailResponseVo {
        return type?.run {
            RecruitDetailResponseVo(
                recruitTitle = this.title,
                recruitIntroduce = this.introduce,
                categories = this.categories,
                plubbingName = this.name,
                plubbingGoal = this.goal,
                plubbingMainImage = this.mainImage,
                plubbingDays = this.days,
                address = this.address,
                roadAdress = this.roadAddress,
                placeName = this.placeName,
                remainAccountNum = this.remainAccountNum,
                curAccountNum = this.curAccountNum,
                plubbingTime = this.time,
                isBookmarked = this.isBookmarked,
                isApplied = this.isApplied,
                joinedAccounts = this.joinedAccounts.map {
                    RecruitDetailJoinedAccountsMapper.mapDtoToModel(it)
                }
            )
        }?: RecruitDetailResponseVo()
    }
}