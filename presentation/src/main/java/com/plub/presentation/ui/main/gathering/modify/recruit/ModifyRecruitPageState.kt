package com.plub.presentation.ui.main.gathering.modify.recruit

import android.os.Parcelable
import com.plub.presentation.ui.PageState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class ModifyRecruitPageState(
    val plubbingId: Int = -1,
    val title: String = "",
    val name: String = "",
    val goal: String = "",
    val introduce: String = "",
    val plubbingMainImgUrl: String = "",
    val tempPlubbingMainBitmap: File? = null
) : PageState, Parcelable {
    @IgnoredOnParcel
    val isNextButtonEnabled = title.isNotBlank() && name.isNotBlank() && goal.isNotBlank()
            && introduce.isNotBlank()
}