package com.plub.presentation.ui.home.plubing.categoryChoice

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.event.CategoryChoiceEvent
import com.plub.presentation.state.CategoryChoiceState
import com.plub.presentation.ui.home.adapter.PlubCardAdapter
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryChoiceFragment :
    BaseFragment<FragmentCategoryChoiceBinding, CategoryChoiceState, CategoryChoiceViewModel>(
        FragmentCategoryChoiceBinding::inflate
    ) {
    private val gatheringListAdapter: PlubCardAdapter by lazy {
        PlubCardAdapter(object : PlubCardAdapter.Delegate {
            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }

            override fun onClickPlubCard(id: Int) {
                goToDetailRecruitment(id)
            }
        })
    }

    private var pages : Int = 1
    private var recommendList : MutableList<RecommendationGatheringResponseContentListVo> = mutableListOf()
    private val mainArgs: MainFragmentArgs by navArgs()
    override val viewModel: CategoryChoiceViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
            recyclerViewCategoryChoiceList.apply {
                layoutManager = GridLayoutManager(context, PlubCardType.TOTAL_SPAN_SIZE).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = gatheringListAdapter.getItemViewType(position)
                            val cardType = PlubCardType.valueOf(viewType)
                            return cardType.spanSize
                        }
                    }
                }
                adapter = gatheringListAdapter

//                addOnScrollListener((object : RecyclerView.OnScrollListener() {
//                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                        super.onScrolled(recyclerView, dx, dy)
//
//                        if (!recyclerViewCategoryChoiceList.canScrollVertically(1)) {
//                            viewModel.fetchRecommendationGatheringData(mainArgs.categoryId.toInt(), ++pages)
//                            scrollToPosition((pages-1) * 10)
//                        }
//                    }
//                }))
            }

            textViewCategoryName.text = mainArgs.categoryName

        }
        viewModel.fetchRecommendationGatheringData(mainArgs.categoryId.toInt(), pages)
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    gatheringListAdapter.submitList(it.cardList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as CategoryChoiceEvent)
                }
            }
        }
    }

    private fun goToDetailRecruitment(plubbingId : Int) {
        val action =
            CategoryChoiceFragmentDirections.actionCategoryChoiceFragmentToRecruitmentFragment(plubbingId.toString())
        findNavController().navigate(action)
    }

    private fun inspectEventFlow(event : CategoryChoiceEvent){
        when(event){
            CategoryChoiceEvent.GoToBack -> { findNavController().popBackStack() }
        }
    }
}