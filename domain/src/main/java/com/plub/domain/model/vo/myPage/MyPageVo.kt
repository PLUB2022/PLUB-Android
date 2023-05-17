package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageViewType

data class MyPageVo(
    val myPageType : MyPageViewType = MyPageViewType.GATHERING,
    val myPageGathering : MyPageGatheringVo = MyPageGatheringVo(),
    val myPageMyProfileVo: MyPageMyProfileVo = MyPageMyProfileVo()
) : DomainModel