package com.plub.presentation.ui.main.archive.bottomsheet.upload

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetArchiveUploadBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ArchiveBottomSheetFragment(private val listener : ArchiveBottomSheetDelegate) : BaseBottomSheetFragment<BottomSheetArchiveUploadBinding, PageState.Default, ArchiveBottomSheetViewModel>(
    BottomSheetArchiveUploadBinding::inflate
) {

    interface ArchiveBottomSheetDelegate{
        fun onSuccessGetImage(file : File?)
    }

    override val viewModel: ArchiveBottomSheetViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ArchiveBottomSheetEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event : ArchiveBottomSheetEvent){
        when(event){
            is ArchiveBottomSheetEvent.GoToCamera -> {
                getImageFromCamera(event.uri)
            }
            is ArchiveBottomSheetEvent.GoToAlbum -> {
                getImageFromGallery()
            }
            is ArchiveBottomSheetEvent.CropImageAndOptimize -> {
                startCropImage(event.cropImageContractOptions)
            }
            is ArchiveBottomSheetEvent.EmitImageFile -> {
                listener.onSuccessGetImage(event.file)
                dismiss()
            }
        }
    }

    private fun getImageFromCamera(uri: Uri) {
        val intent = IntentUtil.getOpenCameraIntent(uri)
        gatheringImageFromCameraResult.launch(intent)
    }

    private val gatheringImageFromCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.proceedGatheringImageFromCameraResult(it)
        }

    private fun getImageFromGallery() {
        val intent = IntentUtil.getSingleImageIntent()
        gatheringImageFromGalleryResult.launch(intent)
    }

    private val gatheringImageFromGalleryResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.proceedGatheringImageFromGalleryResult(result)
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }
}