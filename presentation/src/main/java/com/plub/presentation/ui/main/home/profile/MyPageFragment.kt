package com.plub.presentation.ui.main.profile

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.profile.adapter.MyPageParentGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, PageState.Default, MyPageViewModel>(
        FragmentMyPageBinding::inflate
    ) {

    private val gatheringAdapter : MyPageParentGatheringAdapter by lazy {
        MyPageParentGatheringAdapter()
    }

    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            gatheringAdapter.submitList(arrayListOf(
                MyPageGatheringVo(gatheringList = arrayListOf("테스트용 1번"), gatheringType = 0),
                MyPageGatheringVo(gatheringType = 1),
                MyPageGatheringVo(gatheringType = 2),
                MyPageGatheringVo(gatheringType = 3)))
            initRecycler()
        }
    }

    private fun initRecycler(){
        binding.apply {
            recyclerViewMyGathering.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = gatheringAdapter
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