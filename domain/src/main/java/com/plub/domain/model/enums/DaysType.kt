package com.plub.domain.model.enums

enum class DaysType(val eng: String, val kor: String, val idx: Int) {
    MON("MON","월", 0),
    TUE("TUE", "화", 1),
    WED("WED", "수", 2),
    THR("THR", "목", 3),
    FRI("FRI", "금", 4),
    SAT("SAT", "토", 5),
    SUN("SUN", "일", 6),
    ALL("ALL", "월,화,수,목,금,토,일", 7);

    companion object {
        fun findByEng(eng: String): DaysType {
            return DaysType.values().find {
                it.eng == eng
            } ?: MON
        }

        fun findByKor(kor: String): DaysType {
            return DaysType.values().find {
                it.kor == kor
            } ?: ALL
        }
    }
}