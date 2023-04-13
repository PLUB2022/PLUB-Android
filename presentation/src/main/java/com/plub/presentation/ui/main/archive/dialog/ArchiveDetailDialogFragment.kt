package com.plub.presentation.ui.main.archive.dialog

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeDialogDetailArchiveBinding
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.px
import java.lang.Math.abs

class ArchiveDetailDialogFragment(
    private val detailVo: ArchiveDetailResponseVo
) : DialogFragment() {
    companion object{
        const val MARGIN_HORIZONTAL = 16
        const val MARGIN_NEXT = 8
    }
    private val binding: IncludeDialogDetailArchiveBinding by lazy {
        IncludeDialogDetailArchiveBinding.inflate(layoutInflater)
    }

    private val archiveViewPagerAdapter: ArchiveViewPagerAdapter by lazy {
        ArchiveViewPagerAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        binding.apply {
            imageButtonCancel.onThrottleClick {
                dismiss()
            }
            initRecycler()
            archiveViewPagerAdapter.submitList(detailVo.images)
            textViewSequence.text = getString(R.string.archive_dialog_sequence, detailVo.sequence)
            textViewCreateDate.text = detailVo.createdAt
            textViewTitle.text = detailVo.title
        }
    }

    private fun initRecycler(){
        binding.viewPagerArchiveImage.apply {
            adapter = archiveViewPagerAdapter
            children.forEach { child ->
                if (child is RecyclerView) {
                    child.apply {
                        setPadding(MARGIN_HORIZONTAL.px, 0, MARGIN_HORIZONTAL.px, 0)
                        clipToPadding = false
                    }
                    return@forEach
                }
            }
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.right = MARGIN_NEXT.px
                    outRect.left = MARGIN_NEXT.px
                }
            })
        }
    }
}