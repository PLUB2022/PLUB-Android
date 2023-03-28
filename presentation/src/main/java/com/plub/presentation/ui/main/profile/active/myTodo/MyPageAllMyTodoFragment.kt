package com.plub.presentation.ui.main.profile.active.myTodo

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageAllMyTodoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageAllMyTodoFragment :
    BaseFragment<FragmentMyPageAllMyTodoBinding, MyPageAllMyTodoState, MyPageAllMyTodoViewModel>(
        FragmentMyPageAllMyTodoBinding::inflate
    ) {


    override val viewModel: MyPageAllMyTodoViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {

                }
            }

            launch {
                viewModel.eventFlow.collect{

                }
            }
        }
    }
}