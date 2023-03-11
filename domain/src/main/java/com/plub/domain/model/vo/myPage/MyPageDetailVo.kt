package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo

data class MyPageDetailVo(
    val viewType: MyPageDetailViewType = MyPageDetailViewType.EMPTY,
    val application : AccountsVo = AccountsVo(),
    val title : MyPageDetailTitleVo = MyPageDetailTitleVo()
) : DomainModel
