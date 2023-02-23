package com.plub.presentation.util

import com.plub.domain.model.vo.account.MyInfoResponseVo

object PlubUser {

    var info: MyInfoResponseVo = MyInfoResponseVo()
        private set

    fun updateInfo(info: MyInfoResponseVo) {
        this.info = info
    }
}