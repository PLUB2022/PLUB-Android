package com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage

import android.app.Activity
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringGoalAndIntroduceAndImageBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.ui.dialog.SelectMenuBottomSheetDialog
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
                if (viewModel.uiState.value != CreateGatheringGoalAndIntroduceAndPicturePageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    if (pageState is CreateGatheringGoalAndIntroduceAndPicturePageState)
                        viewModel.initUiState(pageState)
                }
            }

            launch {
                viewModel.showSelectImageBottomSheetDialog.collect {
                    PermissionManager.createGetImagePermission {
                        showBottomSheetDialogSelectImage()
                    }
                }
            }

            launch {
                viewModel.getImageFromCamera.collect {
                    getImageFromCamera(it)
                }
            }

            launch {
                viewModel.getImageFromGallery.collect {
                    getImageFromGallery()
                }
            }
        }
    }

    private fun getImageFromCamera(uri: Uri) {
        val intent = IntentUtil.getOpenCameraIntent(uri)
        gatheringImageFromCameraResult.launch(intent)
    }

    private val gatheringImageFromCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }
}