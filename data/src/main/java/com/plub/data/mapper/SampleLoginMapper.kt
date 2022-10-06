package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin

object SampleLoginMapper: Mapper<SampleLoginResponse, SampleLogin> {
    override fun mapFromEntity(type: SampleLoginResponse): UiState<SampleLogin> {
        return type.run {
            judgeUiState(SampleLogin(login,register))
        }
    }
}