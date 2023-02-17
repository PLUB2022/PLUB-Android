package com.plub.presentation.ui.main.plubing.board.write

import android.text.SpannableString
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.presentation.ui.PageState
import java.io.File

data class BoardWritePageState(
    val feedTypeList: List<WriteBoardFeedTypeVo> = emptyList(),
    val selectedFeedType: PlubingFeedType = PlubingFeedType.IMAGE,
    val imageFile: File? = null,
    val plubingName: String = "",
    var title: String = "",
    var content: String = "",
    val contentCount: SpannableString = SpannableString(""),
    val contentMaxLength: Int = 0,
    val isPostButtonEnable: Boolean = false
) : PageState