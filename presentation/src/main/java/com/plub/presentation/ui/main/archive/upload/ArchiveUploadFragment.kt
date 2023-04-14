package com.plub.presentation.ui.main.archive.upload

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentArchiveUpdateBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.archive.upload.adapter.ArchiveUploadAdapter
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveUploadFragment : BaseTestFragment<FragmentArchiveUpdateBinding, ArchiveUploadPageState, ArchiveUploadViewModel>(
    FragmentArchiveUpdateBinding::inflate
) {

    companion object{
        const val ITEM_SPAN_COUNT_ONE = 1
        const val ITEM_SPAN_COUNT_TWO = 2

        const val HORIZONTAL_SPACE = 6
        const val VERTICAL_SPACE = 4

        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1
    }

    private val gatheringImageFromCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.proceedGatheringImageFromCameraResult(it)
        }

    private val gatheringImageFromGalleryResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.proceedGatheringImageFromGalleryResult(result)
    }

    private val archiveUploadFragmentArgs : ArchiveUploadFragmentArgs by navArgs()
    override val viewModel: ArchiveUploadViewModel by viewModels()

    private val archiveUploadAdapter : ArchiveUploadAdapter by lazy {
        ArchiveUploadAdapter(object : ArchiveUploadAdapter.ArchiveUploadDelegate{
            override fun onClickDelete(position: Int, image : String) {
                viewModel.deleteList(position, image)
            }

            override fun addImage() {
                viewModel.showBottomSheet()
            }

            override fun onChangedText(text: String) {
                viewModel.updateEditText(text)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        initRecycler()
        initUploadView()
    }

    private fun initRecycler(){
        binding.recyclerViewArchiveContent.apply {
            layoutManager = GridLayoutManager(context, ITEM_SPAN_COUNT_TWO).apply {
                spanSizeLookup = object : SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        val archiveType = ArchiveItemViewType.valueOf(archiveUploadAdapter.getItemViewType(position))
                        return when (archiveType) {
                            ArchiveItemViewType.IMAGE_VIEW -> ITEM_SPAN_COUNT_ONE
                            ArchiveItemViewType.IMAGE_ADD_VIEW -> ITEM_SPAN_COUNT_ONE
                            else -> { ITEM_SPAN_COUNT_TWO }
                        }
                    }
                }
            }
            addItemDecoration(GridSpaceDecoration(ITEM_SPAN_COUNT_TWO, HORIZONTAL_SPACE.px, VERTICAL_SPACE.px, false))
            adapter = archiveUploadAdapter
        }
    }

    private fun initUploadView(){
        when(archiveUploadFragmentArgs.type){
            UPLOAD_TYPE -> {
                viewModel.initPageWithFirstImage(archiveUploadFragmentArgs.image)
            }

            EDIT_TYPE -> {
                viewModel.initPage(archiveUploadFragmentArgs.archiveId)
            }
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.uiState.archiveUploadVoList.collect{
                    archiveUploadAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ArchiveUploadEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event : ArchiveUploadEvent){
        when(event){
            is ArchiveUploadEvent.ShowBottomSheet ->{
                showBottomSheetDialogSelectImage()
            }
            is ArchiveUploadEvent.GoToBack -> {
                findNavController().popBackStack()
            }
            is ArchiveUploadEvent.CropImageAndOptimize -> { startCropImage(event.cropImageContractOptions) }
            is ArchiveUploadEvent.GoToAlbum -> { getImageFromGallery() }
            is ArchiveUploadEvent.GoToCamera -> { getImageFromCamera(event.uri) }
        }
    }

    private fun showBottomSheetDialogSelectImage(){
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun getImageFromCamera(uri: Uri) {
        val intent = IntentUtil.getOpenCameraIntent(uri)
        gatheringImageFromCameraResult.launch(intent)
    }

    private fun getImageFromGallery() {
        val intent = IntentUtil.getSingleImageIntent()
        gatheringImageFromGalleryResult.launch(intent)
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }
}