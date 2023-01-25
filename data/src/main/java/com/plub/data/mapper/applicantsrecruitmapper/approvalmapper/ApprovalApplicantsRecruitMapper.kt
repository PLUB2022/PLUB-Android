package com.plub.data.mapper.applicantsrecruitmapper.approvalmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsrecruit.approval.ApprovalApplicantsRecruitResponse
import com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo.ApplicantsRecruitApprovalResponseVo

object ApprovalApplicantsRecruitMapper : Mapper.ResponseMapper<ApprovalApplicantsRecruitResponse, ApplicantsRecruitApprovalResponseVo> {
    override fun mapDtoToModel(type: ApprovalApplicantsRecruitResponse?): ApplicantsRecruitApprovalResponseVo {
        return type?.run {
            ApplicantsRecruitApprovalResponseVo(
                maxAccountNum, curAccountNum
            )
        } ?: ApplicantsRecruitApprovalResponseVo(0,0)
    }
}