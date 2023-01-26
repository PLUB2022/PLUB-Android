package com.plub.data.dto.recruitdetail.host

import com.plub.data.base.DataDto
import com.plub.data.dto.recruitdetail.host.Answers

data class Accounts(
    val accountName: String,
    val profileImage : String,
    val createdAt : String,
    val answers : List<Answers>
) : DataDto