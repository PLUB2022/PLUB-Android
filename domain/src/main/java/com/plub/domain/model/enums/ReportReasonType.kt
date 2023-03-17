package com.plub.domain.model.enums

enum class ReportReasonType(val type : String) {
    BAD_WORDS("BAD_WORDS"), FALSE_FACT("FALSE_FACT"), ADVERTISEMENT("ADVERTISEMENT"), ETC("ETC");

    companion object {
        fun valuesOf(type: String): ReportReasonType {
            return ReportReasonType.values().find {
                it.type == type
            } ?: ReportReasonType.BAD_WORDS
        }
    }
}