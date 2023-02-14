package com.plub.presentation.ui.main.archive.upload

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveUpdateBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.ArchiveFragment
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetFragment
import com.plub.presentation.ui.main.archive.upload.adapter.ArchiveUploadAdapter
import com.plub.presentation.ui.sign.signup.SignUpEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File


class ArchiveUploadFragment : BaseFragment<FragmentArchiveUpdateBinding, ArchiveUploadPageState, ArchiveUploadViewModel>(
    FragmentArchiveUpdateBinding::inflate
) {

    companion object{
        const val ITEM_SPAN_COUNT_LINEAR = 1
        const val ITEM_SPAN_COUNT_GRID = 2

        const val EDIT_VIEW = 0
        const val IMAGE_TITLE_VIEW = 1
        const val IMAGE_VIEW = 2

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
                        return when (archiveUploadAdapter.getItemViewType(position)) {
                            EDIT_VIEW -> ITEM_SPAN_COUNT_LINEAR
                            IMAGE_TITLE_VIEW -> ITEM_SPAN_COUNT_LINEAR
                            IMAGE_VIEW -> ITEM_SPAN_COUNT_GRID
                            else -> ITEM_SPAN_COUNT_LINEAR
                        }
                    }
                }
            }
            adapter = archiveUploadAdapter
        }
    }

    private fun initUploadView(){
        when(archiveUploadFragmentArgs.type){
            UPLOAD_TYPE -> {
                viewModel.initPageWithFirstImage(archiveUploadFragmentArgs.image)
            }

            EDIT_TYPE -> {

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