package com.plub.presentation.ui.main.plubing.board.write

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.canhub.cropper.CropImageContract
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentBoardWriteBinding
import com.plub.presentation.ui.common.decoration.HorizontalSpaceDecoration
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.plubing.board.write.adapter.WriteFeedTypeAdapter
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.px
import com.plub.presentation.util.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoardWriteFragment :
    BaseTestFragment<FragmentBoardWriteBinding, BoardWritePageState, BoardWriteViewModel>(
        FragmentBoardWriteBinding::inflate
    ) {

    companion object {
        const val KEY_RESULT_EDIT_COMPLETE = "KEY_RESULT_EDIT_COMPLETE"
        const val KEY_RESULT_CREATE_COMPLETE = "KEY_RESULT_CREATE_COMPLETE"
        private const val SPACE_FEED_TYPE_HORIZONTAL = 8
    }

    override val viewModel: BoardWriteViewModel by viewModels()

    private val boardWriteArgs: BoardWriteFragmentArgs by navArgs()

    private val cropImageLauncher = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }
    private val albumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            result.data?.data?.let {
                viewModel.onSelectImageFromAlbum(it)
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            viewModel.onTakeImageFromCamera()
        }


    private val feedTypeAdapter: WriteFeedTypeAdapter by lazy {
        WriteFeedTypeAdapter(object : WriteFeedTypeAdapter.Delegate {

            override fun onClickFeedType(feedType: PlubingFeedType) {
                viewModel.onClickFeedType(feedType)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewFeedType.apply {
                addItemDecoration(HorizontalSpaceDecoration(SPACE_FEED_TYPE_HORIZONTAL.px))
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
                adapter = feedTypeAdapter
            }
        }

        viewModel.initArgs(boardWriteArgs)
        viewModel.fetchFeedTypeList()
        viewModel.initEditInfo()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.feedTypeList.collect {
                    feedTypeAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as BoardWriteEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: BoardWriteEvent) {
        when (event) {
            is BoardWriteEvent.ShowSelectImageBottomSheetDialog -> showBottomSheetDialogSelectImage()
            is BoardWriteEvent.CropImageAndOptimize -> cropImageLauncher.launch(event.cropImageContractOptions)
            is BoardWriteEvent.GoToAlbum -> {
                val intent = IntentUtil.getSingleImageIntent()
                albumLauncher.launch(intent)
            }
            is BoardWriteEvent.GoToCamera -> {
                val intent = IntentUtil.getOpenCameraIntent(event.uri)
                cameraLauncher.launch(intent)
            }
            is BoardWriteEvent.CompleteEdit -> {
                setNavigationResult(KEY_RESULT_EDIT_COMPLETE, event.board)
                findNavController().popBackStack()
            }
            is BoardWriteEvent.CompleteCreate -> {
                setNavigationResult(KEY_RESULT_CREATE_COMPLETE, "")
                findNavController().popBackStack()
            }
        }
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }
}