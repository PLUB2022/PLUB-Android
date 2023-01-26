package com.plub.presentation.ui.home.plubing.categoryChoice

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.GatheringShapeType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.event.CategoryChoiceEvent
import com.plub.presentation.event.LoginEvent
import com.plub.presentation.state.CategoryChoiceState
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.ui.home.plubing.categoryChoice.adapter.MainRecommendGridAdapter
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryChoiceFragment :
    BaseFragment<FragmentCategoryChoiceBinding, CategoryChoiceState, CategoryChoiceViewModel>(
        FragmentCategoryChoiceBinding::inflate
    ) {
    companion object{
        const val ITEM_SIZE = 10
    }
    private val categorygridAdapter: MainRecommendGridAdapter by lazy {
        MainRecommendGridAdapter(object : MainRecommendGridAdapter.MainRecommendGridDelegate {
            override fun onClickGoRecruitDetail(plubbingId: Int) {
                goToDetailRecruitment(plubbingId)
            }

            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }
        })
    }
    private val categorylistAdapter: MainRecommendAdapter by lazy {
        MainRecommendAdapter(object : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate {
            override fun onClickGoRecruitDetail(plubbingId: Int) {
                goToDetailRecruitment(plubbingId)
            }

            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
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
            recyclerViewCategoryChoiceList.addOnScrollListener((object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // 스크롤이 끝에 도달했는지 확인
                    if (!recyclerViewCategoryChoiceList.canScrollVertically(1)) {
                        viewModel.fetchRecommendationGatheringData(mainArgs.categoryId.toInt(), ++pages)
                    }
                }
            }))

            textViewCategoryName.text = mainArgs.categoryName

        }
        viewModel.fetchRecommendationGatheringData(mainArgs.categoryId.toInt(), pages)
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.recommendationData.collect {
                    when (it.content.size) {
                        0 -> PlubLogger.logD("CategoryChoiceFragmentTAG", "아무것도 없음")//HasNotDataRecycler()
                        else -> {
                            addRecommendList(it)
                            categorygridAdapter.submitList(recommendList)
                            categorylistAdapter.submitList(recommendList)
                            setListRecycler()
                            binding.recyclerViewCategoryChoiceList.adapter?.notifyItemInserted(it.content.size)
                        }
                    }
                }
            }


            launch {
                viewModel.uiState.collect {
                    when(it.listOrGrid){
                        GatheringShapeType.LIST->setListRecycler()
                        GatheringShapeType.GRID->changeGridRecycler()
                    }
                    changeListAndGridButton(it.listOrGrid)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as CategoryChoiceEvent)
                }
            }
        }
    }

    private fun setListRecycler() {
        binding.recyclerViewCategoryChoiceList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categorylistAdapter
        }
    }

    private fun changeGridRecycler() {
        binding.recyclerViewCategoryChoiceList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categorygridAdapter
        }
    }

    private fun changeListAndGridButton(type : GatheringShapeType){
        when(type){
            GatheringShapeType.LIST->{
                binding.imageBtnList.setImageResource(R.drawable.ic_list_item_active)
                binding.imageBtnGrid.setImageResource(R.drawable.ic_grid_item_inactive)
            }
            GatheringShapeType.GRID->{
                binding.imageBtnList.setImageResource(R.drawable.ic_list_item_inactive)
                binding.imageBtnGrid.setImageResource(R.drawable.ic_grid_item_active)
            }
        }
    }

    private fun goToDetailRecruitment(plubbingId : Int) {
        val action =
            CategoryChoiceFragmentDirections.actionCategoryChoiceFragmentToRecruitmentFragment(plubbingId.toString())
        findNavController().navigate(action)
    }

    private fun backMainPage(){
        //fragmentManager?.popBackStackImmediate()
    }


    private fun addRecommendList(data : RecommendationGatheringResponseVo){
        for(i in data.content){
            recommendList.add(i)
        }
    }

    private fun inspectEventFlow(event : CategoryChoiceEvent){
        when(event){
            CategoryChoiceEvent.GoToMain -> {}
        }
    }
}