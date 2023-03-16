package com.plub.presentation.ui.main.archive.upload

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveUpdateBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetFragment
import com.plub.presentation.ui.main.archive.upload.adapter.ArchiveUploadAdapter
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ArchiveUploadFragment : BaseFragment<FragmentArchiveUpdateBinding, ArchiveUploadPageState, ArchiveUploadViewModel>(
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
                viewModel.initPageWithFirstImage(archiveUploadFragmentArgs.image, archiveUploadFragmentArgs.title, archiveUploadFragmentArgs.plubbingId)
            }

            EDIT_TYPE -> {
                viewModel.initPage(archiveUploadFragmentArgs.plubbingId, archiveUploadFragmentArgs.archiveId, archiveUploadFragmentArgs.title)
            }
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.uiState.collect{
                    archiveUploadAdapter.submitList(it.archiveUploadVoList)
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
        }
    }

    private fun showBottomSheetDialogSelectImage(){
        ArchiveBottomSheetFragment(object : ArchiveBottomSheetFragment.ArchiveBottomSheetDelegate{
            override fun onSuccessGetImage(file: File?) {
                viewModel.uploadImageFile(file)
            }
        }).show(childFragmentManager, "")
    }
}