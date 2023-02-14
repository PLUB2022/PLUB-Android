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
import com.plub.presentation.ui.main.archive.upload.adapter.ArchiveUploadAdapter
import kotlinx.coroutines.launch


class ArchiveUploadFragment : BaseFragment<FragmentArchiveUpdateBinding, ArchiveUploadPageState, ArchiveUploadViewModel>(
    FragmentArchiveUpdateBinding::inflate
) {

    companion object{
        const val EDIT_VIEW = 0
        const val IMAGE_TITLE_VIEW = 1
        const val IMAGE_VIEW = 2

        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1

        const val MAX_IMAGE = 10
    }

    private var editText : String = ""
    private val archiveUploadFragmentArgs : ArchiveUploadFragmentArgs by navArgs()
    override val viewModel: ArchiveUploadViewModel by viewModels()

    private val archiveUploadAdapter : ArchiveUploadAdapter by lazy {
        ArchiveUploadAdapter(object : ArchiveUploadAdapter.ArchiveUploadDelegate{
            override fun onClickDelete(position: Int) {

            }

            override fun addImage() {

            }

            override fun onChangedText(text: String) {
                editText = text
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
            layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        return when (archiveUploadAdapter.getItemViewType(position)) {
                            EDIT_VIEW -> 1
                            IMAGE_TITLE_VIEW -> 1
                            IMAGE_VIEW -> 2
                            else -> 1
                        }
                    }
                }
            }
            adapter = archiveUploadAdapter
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.uiState.collect{
                    subList(it)
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

    private fun initUploadView(){
        when(archiveUploadFragmentArgs.type){
            UPLOAD_TYPE -> {
                viewModel.initPageWithFirstImage(archiveUploadFragmentArgs.image)
            }

            EDIT_TYPE -> {

            }
        }
    }
}