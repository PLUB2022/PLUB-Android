package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.kakaoLocation.KakaoLocationInfoResponse
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoVo

object KakaoLocationDocumentsMapper: Mapper.ResponseMapper<KakaoLocationInfoResponse, KakaoLocationInfoVo> {

    override fun mapDtoToModel(type: KakaoLocationInfoResponse?): KakaoLocationInfoVo {
        val documentsVo = mutableListOf<KakaoLocationInfoDocumentVo>()

        return type?.run {
            documents.forEach { document ->
                documentsVo.add(KakaoLocationInfoDocumentVo(
                    placeName = document.placeName,
                    placePositionX = document.placePositionX,
                    placePositionY = document.placePositionY,
                    roadAddressName = document.roadAddressName
                ))
            }
            return KakaoLocationInfoVo(documentsVo)
        }?: KakaoLocationInfoVo(documentsVo)
    }
}