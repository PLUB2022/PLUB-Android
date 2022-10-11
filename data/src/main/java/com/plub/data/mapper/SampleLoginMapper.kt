package com.plub.data.mapper

import com.plub.data.UiStateCallback
import com.plub.data.base.Mapper
import com.plub.data.model.SampleLoginResponse
import com.plub.domain.model.SampleLogin

object SampleLoginMapper: Mapper<SampleLoginResponse, SampleLogin>() {

    override suspend fun mapFromEntity(type: SampleLoginResponse, result: UiStateCallback<SampleLogin>) {
        type.run {
            val model = SampleLogin(login, register)
            mapToUiState(customCode, model, result)
        }
    }
}