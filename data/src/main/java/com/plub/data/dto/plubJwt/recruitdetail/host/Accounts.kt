package com.plub.data.dto.plubJwt.recruitdetail.host

import com.plub.data.base.DataDto

data class Accounts(
    val accountName: String,
    val profileImage : String,
    val createdAt : String,
    val answers : List<Answers>
) : DataDto
