package com.plub.presentation.ui.main.plubing.board

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingBoardBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlubingBoardFragment : BaseFragment<FragmentPlubingBoardBinding, PageState.Default, PlubingBoardViewModel>(
    FragmentPlubingBoardBinding::inflate
) {

    override val viewModel: PlubingBoardViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

        }
    }
}