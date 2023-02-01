package com.plub.domain.model.vo.signUp.profile

import com.plub.domain.model.DomainModel
import kotlinx.coroutines.CoroutineScope

data class NicknameCheckRequestVo(
    val nickname:String,
    val scope: CoroutineScope
): DomainModel