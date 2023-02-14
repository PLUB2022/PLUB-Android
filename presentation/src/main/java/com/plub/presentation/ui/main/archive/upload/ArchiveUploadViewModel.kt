package com.plub.presentation.ui.main.archive.upload

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.domain.usecase.GetDetailArchiveUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetEvent
import com.plub.presentation.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveUploadViewModel @Inject constructor(
    private val getDetailArchiveUseCase: GetDetailArchiveUseCase
) : BaseViewModel<ArchiveUploadPageState>(ArchiveUploadPageState()) {

    fun initPageWithFirstImage(imageUri : String){
        val firstImageList = arrayListOf<ArchiveUploadVo>(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_VIEW, image = imageUri))
        val mergeList = getEmptyMergedList()
        updateListState(mergeList + firstImageList)
    }

    private fun getEmptyMergedList() : List<ArchiveUploadVo>{
        val list = mutableListOf<ArchiveUploadVo>()
        list.add(ArchiveUploadVo(viewType = ArchiveItemViewType.EDIT_VIEW))
        list.add(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_TEXT_VIEW))
        return list
    }

    private fun updateListState(stateList : List<ArchiveUploadVo>){
        val imageCount = stateList.size - 1
        updateUiState { uiState ->
            uiState.copy(
                archiveUploadVoList = stateList,
                imageCount = imageCount
            )
        }
    }
}