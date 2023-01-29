package com.plub.presentation.ui.home.plubing.categoryChoice

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.event.CategoryChoiceEvent
import com.plub.presentation.state.CategoryChoiceState
import com.plub.presentation.ui.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.ui.home.adapter.PlubCardAdapter
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import com.plub.presentation.util.PlubLogger
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
            includeDataEmpty.buttonGoCreateGathering.setOnClickListener {
                goToCreateGatheringFragment()
            }
        }
        viewModel.fetchRecommendationGatheringData(mainArgs.categoryId.toInt(), pages)
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    gatheringListAdapter.submitList(it.cardList)
                    setSortTypeText(it.sortType)
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
            CategoryChoiceFragmentDirections.actionCategoryChoiceToRecruitment(plubbingId.toString())
        findNavController().navigate(action)
    }

    private fun inspectEventFlow(event : CategoryChoiceEvent){
        when(event){
            is CategoryChoiceEvent.GoToBack -> {
                findNavController().popBackStack()
            }
            is CategoryChoiceEvent.ShowSelectSortTypeBottomSheetDialog -> {
                showSelectSortTypeDialog(event.selectedItem)
            }
            is CategoryChoiceEvent.GoToSearch -> {
                goToSearchFragment()
            }
            is CategoryChoiceEvent.GoToCreate -> {
                PlubLogger.logD("생성페이지로")
                goToCreateGatheringFragment()
            }
        }
    }

    private fun showSelectSortTypeDialog(selectedMenuType: DialogMenuItemType) {
        val title = getString(R.string.word_sort)
        SelectMenuBottomSheetDialog.newInstance(
            DialogMenuType.SORT_TYPE,
            title,
            DialogMenuAdapter.VIEW_TYPE_SPINNER,
            selectedMenuType
        ) {
            viewModel.onClickSortMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun setSortTypeText(sortType: PlubSortType) {
        val sortTypeRes = when (sortType) {
            PlubSortType.POPULAR -> R.string.word_sort_type_popular
            PlubSortType.NEW -> R.string.word_sort_type_new
        }
        binding.textViewSortType.text = getString(sortTypeRes)
    }

    private fun goToSearchFragment() {
        val action = CategoryChoiceFragmentDirections.actionCategoryChoiceToSearching()
        findNavController().navigate(action)
    }

    private fun goToCreateGatheringFragment() {
        val action = CategoryChoiceFragmentDirections.actionCategoryChoiceToCreateGathering()
        findNavController().navigate(action)
    }
}