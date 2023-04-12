package com.plub.presentation.ui.main.home.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageBinding
import com.plub.presentation.ui.main.home.profile.adapter.MyPageParentGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageState, MyPageViewModel>(
        FragmentMyPageBinding::inflate
    ) {

    companion object{
        const val LEAST_LINE = 1
    }

    private val gatheringAdapter : MyPageParentGatheringAdapter by lazy {
        MyPageParentGatheringAdapter(object : MyPageParentGatheringAdapter.MyPageDelegate{
            override fun onClickCardExpand(gatheringType: MyPageGatheringStateType) {
                viewModel.onClickExpand(gatheringType)
            }

            override fun onClickGathering(
                gatheringParentType: MyPageGatheringStateType,
                gatheringType: MyPageGatheringMyType,
                plubbingId : Int
            ) {
                viewModel.goToDetail(gatheringParentType, plubbingId)
            }

        })
    }

    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
        viewModel.getMyPageData()
        viewModel.setMyInfo()
        initRecycler()
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
                    gatheringAdapter.submitList(it.myPageGatheringList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as MyPageEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.GoToMyApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToWaitingGathering(event.plubbingId))}
            is MyPageEvent.GoToOtherApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToRecruitingGathering(event.plubbingId))}
            is MyPageEvent.ReadMore -> { readMoreIntro(event.isExpandText) }
            is MyPageEvent.GoToEdit -> {findNavController().navigate(MyPageFragmentDirections.myPageToEdit())}
            is MyPageEvent.GoToSetting -> {findNavController().navigate(MyPageFragmentDirections.myPageToSetting())}
        }
    }

    private fun readMoreIntro(isExpandText : Boolean){
        binding.apply {
            if(isExpandText){
                textViewReadMore.text = getString(R.string.my_page_close)
                textViewProfileExplain.maxLines = Int.MAX_VALUE
            }
            else{
                textViewReadMore.text = getString(R.string.my_page_read_more)
                textViewProfileExplain.maxLines = LEAST_LINE
            }
        }
    }
}