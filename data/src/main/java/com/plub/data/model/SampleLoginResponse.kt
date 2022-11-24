package com.plub.data.model

import com.plub.data.base.DataDto
import com.plub.data.base.DataEntity

data class SampleLoginResponse(
    val login: String,
    val register: String
):DataDto
