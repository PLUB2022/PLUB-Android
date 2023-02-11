package com.plub.presentation.ui.main.archive

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<FragmentArchiveBinding, PageState.Default, ArchiveViewModel>(
    FragmentArchiveBinding::inflate
){
    override val viewModel: ArchiveViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        //TODO("Not yet implemented")
    }
}