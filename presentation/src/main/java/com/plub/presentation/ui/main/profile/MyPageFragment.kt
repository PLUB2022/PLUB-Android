package com.plub.presentation.ui.main.profile

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, PageState.Default, MyPageViewModel>(
        FragmentMyPageBinding::inflate
    ) {

    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            initRecycler()
        }
    }

    private fun initRecycler(){
        binding.apply {
            recyclerViewMyGathering.apply {

            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {

                }
            }
        }
    }
}