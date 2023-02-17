package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.graphics.Bitmap
import android.os.Parcelable
import com.plub.presentation.ui.PageState
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModifyRecruitPageState(
    val title: String = "",
    val name: String = "",
    val goal: String = "",
    val introduce: String = "",
    val plubbingMainImgUrl: String = "",
    val tempPlubbingMainBitmap: Bitmap? = null
) : PageState, Parcelable