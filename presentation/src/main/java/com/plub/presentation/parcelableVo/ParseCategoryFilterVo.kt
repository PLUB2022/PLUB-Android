package com.plub.presentation.parcelableVo

import android.os.Parcelable
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.categoriesGatheringVo.FilterVo
import com.plub.presentation.ui.main.home.categoryGathering.filter.GatheringFilterState
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParseCategoryFilterVo(
    val gatheringDays: HashSet<DaysType> = hashSetOf(),
    val accountNum : Int = 0,
    val selectedHobbies:List<SelectedHobbyVo> = emptyList()
) : Parcelable {

    companion object : ParseModel<ParseCategoryFilterVo, FilterVo> {
        override fun mapToParse(vo: FilterVo): ParseCategoryFilterVo {
            return vo.run {
                ParseCategoryFilterVo(
                    gatheringDays, accountNum, selectedHobbies
                )
            }
        }

        override fun mapToDomain(vo: ParseCategoryFilterVo): FilterVo {
            return vo.run {
                FilterVo(
                    gatheringDays, accountNum, selectedHobbies
                )
            }
        }
    }
}