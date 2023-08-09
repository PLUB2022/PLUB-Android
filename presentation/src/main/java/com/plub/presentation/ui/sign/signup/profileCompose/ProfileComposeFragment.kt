package com.plub.presentation.ui.sign.signup.profileCompose

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.SignUpPageType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentProfileComposeBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import com.plub.presentation.util.IntentUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileComposeFragment :
    BaseFragment<FragmentProfileComposeBinding, ProfileComposePageState, ProfileComposeViewModel>(
        FragmentProfileComposeBinding::inflate
    ) {

    override val viewModel: ProfileComposeViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private lateinit var albumLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
        setAlbumLauncher()
        setCameraLauncher()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as ProfileComposeEvent)
                }
            }

            launch {
                viewModel.uiState.collect {
                    nicknameIsActiveState(it.nicknameIsActive)
                }
            }

            launch {
                parentViewModel.uiState.collect {
                    viewModel.onInitProfileComposeVo(it.profileComposeVo)
                }
            }
        }
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE_HAS_DEFAULT) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager,"")
    }

    private fun setAlbumLauncher() {
        albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            result.data?.data?.let {
                viewModel.onSelectImageFromAlbum(it)
            }
        }
    }

    private fun setCameraLauncher() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            viewModel.onTakeImageFromCamera()
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    private fun inspectEventFlow(event: ProfileComposeEvent) {
        when(event) {
            is ProfileComposeEvent.GoToAlbum -> {
                val intent = IntentUtil.getSingleImageIntent()
                albumLauncher.launch(intent)
            }
            is ProfileComposeEvent.GoToCamera -> {
                val intent = IntentUtil.getOpenCameraIntent(event.uri)
                cameraLauncher.launch(intent)
            }
            is ProfileComposeEvent.MoveToNext -> {
                parentViewModel.onMoveToNextPage(SignUpPageType.PROFILE, event.vo)
            }
            is ProfileComposeEvent.ShowSelectImageBottomSheetDialog -> showBottomSheetDialogSelectImage()
            is ProfileComposeEvent.CropImageAndOptimize -> startCropImage(event.cropImageContractOptions)
        }
    }

    private fun nicknameIsActiveState(isNicknameActive: Boolean?) {
        binding.apply {
            when (isNicknameActive) {
                true -> {
                    val textColor = ContextCompat.getColor(requireContext(), R.color.color_363636)
                    val color = ContextCompat.getColor(requireContext(), R.color.color_5f5ff9)
                    constraintLayoutNickname.setBackgroundResource(R.drawable.bg_rectangle_empty_5f5ff9_radius_8)
                    editTextNickname.setTextColor(textColor)
                    textViewNicknameDescription.setTextColor(color)
                    imageViewNicknameDescription.setImageResource(R.drawable.ic_check_circle_5f5ff9)
                }
                false -> {
                    val color = ContextCompat.getColor(requireContext(), R.color.color_f75b2b)
                    constraintLayoutNickname.setBackgroundResource(R.drawable.bg_rectangle_empty_f75b2b_radius_8)
                    editTextNickname.setTextColor(color)
                    textViewNicknameDescription.setTextColor(color)
                    imageViewNicknameDescription.setImageResource(R.drawable.ic_tooltip_f75b2b)
                }
                null -> {
                    val textColor = ContextCompat.getColor(requireContext(), R.color.color_363636)
                    val color = ContextCompat.getColor(requireContext(), R.color.color_c4c4c4)
                    constraintLayoutNickname.setBackgroundResource(R.drawable.bg_rectangle_empty_c4c4c4_radius_8)
                    editTextNickname.setTextColor(textColor)
                    textViewNicknameDescription.setTextColor(color)
                    imageViewNicknameDescription.setImageResource(R.drawable.ic_tooltip_c4c4c4)
                }
            }
        }
    }
}