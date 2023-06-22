package com.plub.presentation.ui.main.gathering.modify.recruit

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyRecruitBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyRecruitFragment : BaseFragment<
        FragmentModifyRecruitBinding, ModifyRecruitPageState, ModifyRecruitViewModel>(
    FragmentModifyRecruitBinding::inflate
) {
    override val viewModel: ModifyRecruitViewModel by viewModels()
    private val navArgs: ModifyRecruitFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        viewModel.initPageState(navArgs.pageState)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    when (it) {
                        is ModifyRecruitEvent.ShowSelectImageBottomSheetDialog -> {
                            PermissionManager.createGetImagePermission {
                                showBottomSheetDialogSelectImage()
                            }
                        }

                        is ModifyRecruitEvent.GetImageFromCamera -> {
                            getImageFromCamera(it.uri)
                        }

                        is ModifyRecruitEvent.GetImageFromGallery -> {
                            getImageFromGallery()
                        }

                        is ModifyRecruitEvent.CropImageAndOptimize -> {
                            startCropImage(it.cropImageContractOptions)
                        }

                        is ModifyRecruitEvent.GoToBack -> {
                            findNavController().popBackStack()
                        }
                    }
                }
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
    ) {
        viewModel.proceedGatheringImageFromGalleryResult(it)
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE_HAS_DEFAULT) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }
}
