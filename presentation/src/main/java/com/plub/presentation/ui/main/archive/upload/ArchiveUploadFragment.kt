package com.plub.presentation.ui.main.archive.upload

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveUpdateBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.upload.adapter.ArchiveUploadAdapter
import kotlinx.coroutines.launch


class ArchiveUploadFragment : BaseFragment<FragmentArchiveUpdateBinding, PageState.Default, ArchiveUploadViewModel>(
    FragmentArchiveUpdateBinding::inflate
) {

    companion object{
        const val EDIT_VIEW = 0
        const val IMAGE_TITLE_VIEW = 1
        const val IMAGE_VIEW = 2
    }

    private val archiveUploadAdapter : ArchiveUploadAdapter by lazy {
        ArchiveUploadAdapter(object : ArchiveUploadAdapter.ArchiveUploadDelegate{

        })
    }
    override val viewModel: ArchiveUploadViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            initRecycler()
        }
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
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner){
            launch {

            }
        }
    }
}