package com.plub.data.base

import com.google.gson.annotations.SerializedName
import com.plub.domain.UiState
import com.plub.domain.base.DomainModel

open class DataEntity {

    companion object {
        const val SUCCESS_CODE:Int = 200
    }
    @SerializedName("code")
    val customCode:Int = 0

    private fun isSuccessCode() = customCode == SUCCESS_CODE

    fun <D:DomainModel>judgeUiState(domainModel: D):UiState<D> {
        return if(isSuccessCode()) UiState.Success(domainModel)
        else UiState.Failure(domainModel, customCode)
    }
}