package com.plub.presentation.ui.main.plubing.todo

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingTodoBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlubingTodoFragment : BaseFragment<FragmentPlubingTodoBinding, PageState.Default, PlubingTodoViewModel>(
    FragmentPlubingTodoBinding::inflate
) {

    override val viewModel: PlubingTodoViewModel by viewModels()

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