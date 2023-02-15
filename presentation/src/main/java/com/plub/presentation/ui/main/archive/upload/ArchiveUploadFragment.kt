package com.plub.presentation.ui.main.archive.upload

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveUpdateBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.main.archive.ArchiveFragment
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
        const val ITEM_SPAN_COUNT_LINEAR = 1
        const val ITEM_SPAN_COUNT_GRID = 2

        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1

        const val MAX_IMAGE = 10
    }

    private val archiveUploadFragmentArgs : ArchiveUploadFragmentArgs by navArgs()
    override val viewModel: ArchiveUploadViewModel by viewModels()

    private val archiveUploadAdapter : ArchiveUploadAdapter by lazy {
        ArchiveUploadAdapter(object : ArchiveUploadAdapter.ArchiveUploadDelegate{
            override fun onClickDelete(position: Int) {
                viewModel.deleteList(position)
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
            layoutManager = GridLayoutManager(context, ITEM_SPAN_COUNT_GRID).apply {
                spanSizeLookup = object : SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        PlubLogger.logD("${ArchiveItemViewType.valueOf(archiveUploadAdapter.getItemViewType(position))}")
                        return when (ArchiveItemViewType.valueOf(archiveUploadAdapter.getItemViewType(position))) {
                            ArchiveItemViewType.EDIT_VIEW -> ITEM_SPAN_COUNT_GRID
                            ArchiveItemViewType.IMAGE_TEXT_VIEW -> ITEM_SPAN_COUNT_GRID
                            ArchiveItemViewType.IMAGE_VIEW -> ITEM_SPAN_COUNT_LINEAR
                            ArchiveItemViewType.IMAGE_ADD_VIEW -> ITEM_SPAN_COUNT_LINEAR
                        }
                    }
                }
            }
            addItemDecoration(GridSpaceDecoration(ITEM_SPAN_COUNT_GRID, 6.px, 4.px, false))
            adapter = archiveUploadAdapter
        }
    }

    private fun initUploadView(){
        when(archiveUploadFragmentArgs.type){
            UPLOAD_TYPE -> {
                viewModel.initPageWithFirstImage(archiveUploadFragmentArgs.image)
            }

            EDIT_TYPE -> {
                viewModel.initPage(archiveUploadFragmentArgs.plubbingId, archiveUploadFragmentArgs.archiveId)
            }
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.uiState.collect{
                    subList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ArchiveUploadEvent)
                }
            }
        }
    }

    private fun subList(vo : ArchiveUploadPageState){
        if(vo.imageCount < MAX_IMAGE){
            val last = arrayListOf(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_ADD_VIEW))
            archiveUploadAdapter.submitList(vo.archiveUploadVoList + last)
        }
        else{
            archiveUploadAdapter.submitList(vo.archiveUploadVoList)
        }
    }

    private fun inspectEventFlow(event : ArchiveUploadEvent){
        when(event){
            is ArchiveUploadEvent.ShowBottomSheet ->{
                showBottomSheetDialogSelectImage()
            }
        }
    }

    private fun showBottomSheetDialogSelectImage(){
        ArchiveBottomSheetFragment(object : ArchiveBottomSheetFragment.ArchiveBottomSheetDelegate{
            override fun onSuccessGetImage(file: File?) {
                viewModel.uploadImageFile(file)
            }
        }).show(childFragmentManager, ArchiveFragment.ARCHIVE_UPLOAD_BOTTOM_SHEET_TAG)
    }
}