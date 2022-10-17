package com.plub.data.model

data class PlubJwtTokenResponse(
    val accesstoken : String,
    val refreshtoken : String
)