package com.plub.presentation.ui.main.profile.active

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageActiveGatheringBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.profile.active.adapter.ActiveGatheringParentAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActiveGatheringFragment :
    BaseFragment<FragmentMyPageActiveGatheringBinding, ActiveGatheringPageState, ActiveGatheringViewModel>(
        FragmentMyPageActiveGatheringBinding::inflate
    ) {

    companion object{
        const val VERTICAL_SPACE = 24
    }

    private val activeGatheringFragmentArgs : ActiveGatheringFragmentArgs by navArgs()

    private val activeGatheringParentAdapter: ActiveGatheringParentAdapter by lazy {
        ActiveGatheringParentAdapter(object : ActiveGatheringParentAdapter.ActiveGatheringDelegate{
            override fun onClickBoard(feedId: Int) {
                viewModel.onClickBoard(feedId)
            }
        })
    }

    override val viewModel: ActiveGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewMyPageContent.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = activeGatheringParentAdapter
            }

        }
        viewModel.setPlubIdAndStateType(activeGatheringFragmentArgs.plubbingId, activeGatheringFragmentArgs.stateType)
        viewModel.setView()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    activeGatheringParentAdapter.submitList(it.detailList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ActiveGatheringEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: ActiveGatheringEvent) {
        when (event) {
            is ActiveGatheringEvent.GoToDetailBoard -> goToDetailBoard(event.feedId)
            is ActiveGatheringEvent.GoToPlubbingMain -> goToPlubbingMain(event.plubbingId)
        }
    }

    private fun goToDetailBoard(feedId: Int) {
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubingBoardDetail(feedId)
        findNavController().navigate(action)
    }

    private fun goToPlubbingMain(plubbingId : Int){
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubbingMain(plubbingId)
        findNavController().navigate(action)
    }
}