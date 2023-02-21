package com.plub.presentation.ui.main.gathering.modifyGathering.guestQuestion

import android.app.Activity
import android.net.Uri
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.UiState
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.usecase.PostChangeFileUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutModifyRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ModifyGuestQuestionViewModel @Inject constructor(

) : BaseViewModel<ModifyGuestQuestionPageState>(ModifyGuestQuestionPageState()) {


    fun initPageState(bundlePageState: ModifyGuestQuestionPageState) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = bundlePageState.plubbingId,
                title = bundlePageState.title,
                name = bundlePageState.name,
                goal = bundlePageState.goal,
                introduce = bundlePageState.introduce,
                plubbingMainImgUrl = bundlePageState.plubbingMainImgUrl,
                tempPlubbingMainBitmap = bundlePageState.tempPlubbingMainBitmap
            )
        }
    }

}