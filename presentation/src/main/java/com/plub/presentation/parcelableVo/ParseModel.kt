package com.plub.presentation.parcelableVo

interface ParseModel<PARSE,DOMAIN> {
    fun mapToParse(vo:DOMAIN):PARSE
    fun mapToDomain(vo:PARSE):DOMAIN
}