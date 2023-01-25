package com.plub.presentation.ui.home.plubing.categoryChoice

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.R
import com.plub.presentation.state.SampleHomeState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.state.CategoryChoiceState
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.home.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGridAdapter
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryChoiceFragment :
    BaseFragment<FragmentCategoryChoiceBinding, CategoryChoiceState, CategoryChoiceViewModel>(
        FragmentCategoryChoiceBinding::inflate
    ) {
    private val categorygridAdapter: MainRecommendGridAdapter by lazy {
        MainRecommendGridAdapter(object : MainRecommendGridAdapter.MainRecommendGridDelegate {
            //TODO 리스너 달기
        })
    }
    private val categorylistAdapter: MainRecommendAdapter by lazy {
        MainRecommendAdapter(object : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate {
            //TODO 리스너 달기
            override fun onClick(plubbingId: Int) {
                //TODO
            }
        })
    }

    private val categoryId: MainFragmentArgs by navArgs()
    override val viewModel: CategoryChoiceViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.fetchRecommendationGatheringData(categoryId.categoryId.toInt())
            //TODO 할 일
        }
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.recommendationData.collect {
                    when (it.plubbings.content.size) {
                        0 -> Log.d("TAG", "nothing")//HasNotDataRecycler()
                        else -> {
                            categorylistAdapter.submitList(it.plubbings.content)
                            setListRecycler()
                        }
                    }
                }
            }


            launch {
                viewModel.uiState.collect {
                    if (it.listOrGrid) {
                        changeGridRecycler()
                    } else {
                        setListRecycler()
                    }
                    changeListAndGridButton(it.listOrGrid)
                }
            }
        }
    }

    fun setListRecycler() {
        binding.recyclerViewCategoryChoiceList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categorylistAdapter
        }
    }

    fun changeGridRecycler() {
        binding.recyclerViewCategoryChoiceList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categorygridAdapter
        }
    }

    fun changeListAndGridButton(flag : Boolean){
        if(flag){
            binding.imageBtnList.setImageResource(R.drawable.ic_list_item_inactive)
            binding.imageBtnGrid.setImageResource(R.drawable.ic_grid_item_active)
        }
        else{
            binding.imageBtnList.setImageResource(R.drawable.ic_list_item_active)
            binding.imageBtnGrid.setImageResource(R.drawable.ic_grid_item_inactive)
        }
    }

    fun goToDetailRecruitment() {
        val action =
            CategoryChoiceFragmentDirections.actionCategoryChoiceFragmentToRecruitmentFragment()
        findNavController().navigate(action)
    }
}