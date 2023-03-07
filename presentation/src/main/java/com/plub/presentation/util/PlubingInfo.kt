package com.plub.presentation.util

import com.plub.domain.model.vo.plub.PlubingMainVo

object PlubingInfo {

    var info: PlubingMainVo = PlubingMainVo()
        private set

    fun updateInfo(info: PlubingMainVo) {
        this.info = info
    }
}