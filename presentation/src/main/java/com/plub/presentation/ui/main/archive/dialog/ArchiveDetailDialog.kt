package com.plub.presentation.ui.main.archive.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeDialogDetailArchiveBinding

class ArchiveDetailDialog(
    private val detailVo : ArchiveDetailResponseVo
) : DialogFragment(){
    private val binding : IncludeDialogDetailArchiveBinding by lazy {
        IncludeDialogDetailArchiveBinding.inflate(layoutInflater)
    }

    private val archiveViewPagerAdapter : ArchiveViewPagerAdapter by lazy {
        ArchiveViewPagerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){
        binding.apply {
            archiveViewPagerAdapter.submitList(detailVo.images)
            viewPagerArchiveImage.apply {
                adapter = archiveViewPagerAdapter
            }
            textViewSequence.text = getString(R.string.archive_dialog_sequence, detailVo.sequence)
            textViewCreateDate.text = detailVo.createdAt
            textViewTitle.text = detailVo.title
        }
    }
}