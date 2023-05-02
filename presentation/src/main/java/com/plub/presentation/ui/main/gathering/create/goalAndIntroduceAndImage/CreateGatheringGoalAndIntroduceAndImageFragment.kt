package com.plub.presentation.ui.main.gathering.create.goalAndIntroduceAndImage

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringGoalAndIntroduceAndImageBinding
import com.plub.presentation.ui.main.gathering.create.CreateGatheringViewModel
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringGoalAndIntroduceAndImageFragment :
    BaseFragment<
            FragmentCreateGatheringGoalAndIntroduceAndImageBinding,
            CreateGatheringGoalAndIntroduceAndPicturePageState,
            CreateGatheringGoalAndIntroduceAndImageViewModel>
        (FragmentCreateGatheringGoalAndIntroduceAndImageBinding::inflate) {

    override val viewModel: CreateGatheringGoalAndIntroduceAndImageViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        with(binding) {
            vm = viewModel
            parentVm = parentViewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    viewModel.initUiState(pageState)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is com.plub.presentation.ui.main.gathering.create.CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.setChildrenPageState(viewModel.uiState.value)
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    when (it) {
                        is CreateGatheringGoalAndIntroduceAndImageEvent.ShowSelectImageBottomSheetDialog -> {
                            PermissionManager.createGetImagePermission {
                                showBottomSheetDialogSelectImage()
                            }
                        }

                        is CreateGatheringGoalAndIntroduceAndImageEvent.GetImageFromCamera -> {
                            getImageFromCamera(it.uri)
                        }

                        is CreateGatheringGoalAndIntroduceAndImageEvent.GetImageFromGallery -> {
                            getImageFromGallery()
                        }

                        is CreateGatheringGoalAndIntroduceAndImageEvent.CropImageAndOptimize -> {
                            startCropImage(it.cropImageContractOptions)
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