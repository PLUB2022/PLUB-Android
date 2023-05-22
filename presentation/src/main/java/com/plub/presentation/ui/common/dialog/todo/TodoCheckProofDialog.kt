package com.plub.presentation.ui.common.dialog.todo

import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.databinding.DialogTodoCheckProofBinding
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.PlubUser
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class TodoCheckProofDialog : DialogFragment() {

    interface Delegate {
        fun onClickLateProof(todoId: Int)
        fun onClickComplete(todoId: Int, proofFile: File)
    }

    companion object {
        private const val KEY_PARSE_TODO_ITEM_ID = "KEY_PARSE_TODO_ITEM_ID"

        fun newInstance(parseTodoItemVo: ParseTodoItemVo, delegate: Delegate) =
            TodoCheckProofDialog().apply {
                this.delegate = delegate
                arguments = Bundle().apply {
                    putParcelable(KEY_PARSE_TODO_ITEM_ID, parseTodoItemVo)
                }
            }
    }

    @Inject
    lateinit var imageUtil: ImageUtil
    private var delegate: Delegate? = null
    private var cameraTempImageUri: Uri? = null
    private var proofFile: File? = null

    private val cropImageLauncher = registerForActivityResult(CropImageContract()) { result ->
        proceedCropImageResult(result)
    }
    private val albumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            result.data?.data?.let {
                onSelectImageFromAlbum(it)
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            onTakeImageFromCamera()
        }

    private val binding: DialogTodoCheckProofBinding by lazy {
        DialogTodoCheckProofBinding.inflate(layoutInflater)
    }

    private val todoItemVo: ParseTodoItemVo by lazy {
        arguments?.parcelable(KEY_PARSE_TODO_ITEM_ID) ?: ParseTodoItemVo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.apply {
            imageViewBack.onThrottleClick {
                dismiss()
            }
            imageViewProof.onThrottleClick {
                PermissionManager.createGetImagePermission {
                    showBottomSheetDialogSelectImage()
                }
            }
            buttonLateProof.onThrottleClick {
                dismiss()
                delegate?.onClickLateProof(todoItemVo.todoId)
            }
            buttonProofComplete.onThrottleClick {
                proofFile?.let {
                    dismiss()
                    delegate?.onClickComplete(todoItemVo.todoId, it)
                }
            }

            textViewTodoContent.apply {
                text = todoItemVo.content
                paintFlags = paintFlags or (Paint.STRIKE_THRU_TEXT_FLAG)
            }
            textViewNickname.text = PlubUser.info.nickname
            textViewDate.text = todoItemVo.date
            PlubUser.info.profileImage?.let {
                GlideUtil.loadImage(requireContext(),
                    it, circleImageViewProfile)
            }
        }
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE) {
            onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when (type) {
            DialogMenuItemType.CAMERA_IMAGE -> {
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    goToCamera(it)
                }
            }

            DialogMenuItemType.ALBUM_IMAGE -> goToAlbum()
            else -> Unit
        }
    }

    private fun goToAlbum() {
        val intent = IntentUtil.getSingleImageIntent()
        albumLauncher.launch(intent)
    }

    private fun goToCamera(uri: Uri) {
        val intent = IntentUtil.getOpenCameraIntent(uri)
        cameraLauncher.launch(intent)
    }

    private fun onTakeImageFromCamera() {
        cameraTempImageUri?.let { uri ->
            cropImageAndOptimize(uri)
        }
    }

    private fun onSelectImageFromAlbum(uri: Uri) {
        cropImageAndOptimize(uri)
    }

    private fun cropImageAndOptimize(uri: Uri) {
        val option = imageUtil.getCropImageOptions(uri)
        cropImageLauncher.launch(option)
    }

    private fun proceedCropImageResult(result: CropImageView.CropResult) {
        if (result.isSuccessful) {
            result.uriContent?.let { uri ->
                completedProofImage(uri)
            }
        }
    }

    private fun completedProofImage(uri: Uri) {
        proofFile = imageUtil.uriToOptimizeImageFile(uri)?.also {
            binding.apply {
                buttonProofComplete.isEnabled = true
                imageViewProofDefault.visibility = View.GONE
                textViewProofDefault.visibility = View.GONE
                GlideUtil.loadImage(requireContext(), it, imageViewProof)
            }
        }
    }
}