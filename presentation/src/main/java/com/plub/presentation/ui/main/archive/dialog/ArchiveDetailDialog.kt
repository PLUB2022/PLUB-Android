package com.plub.presentation.ui.main.archive.dialog

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeDialogDetailArchiveBinding
import com.plub.presentation.util.px

class ArchiveDetailDialog(
    private val detailVo: ArchiveDetailResponseVo
) : DialogFragment() {
    private val binding: IncludeDialogDetailArchiveBinding by lazy {
        IncludeDialogDetailArchiveBinding.inflate(layoutInflater)
    }

    private val archiveViewPagerAdapter: ArchiveViewPagerAdapter by lazy {
        ArchiveViewPagerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        binding.apply {
            initRecycler()
            archiveViewPagerAdapter.submitList(detailVo.images)
            textViewSequence.text = getString(R.string.archive_dialog_sequence, detailVo.sequence)
            textViewCreateDate.text = detailVo.createdAt
            textViewTitle.text = detailVo.title
        }
    }

    private fun initRecycler(){
        binding.viewPagerArchiveImage.apply {
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.right = 16.px
                    outRect.left = 16.px
                }
            })
            setPageTransformer { page, position ->
                page.translationX = -(24.px) * (position)
            }
            offscreenPageLimit = 1
            adapter = archiveViewPagerAdapter
        }
    }
}