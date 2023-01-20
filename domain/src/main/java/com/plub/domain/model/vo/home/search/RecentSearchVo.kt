package com.plub.domain.model.vo.home.search

data class RecentSearchVo(
    val id:Int = -1,
    val search:String = "",
    val saveTime:Long = -1
)