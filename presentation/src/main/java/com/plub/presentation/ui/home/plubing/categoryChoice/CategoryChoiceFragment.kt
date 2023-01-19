package com.plub.presentation.ui.home.plubing.categoryChoice

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.state.CategoryChoiceState
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
                        Log.d("최하단", "최하단")
                    }
                }
            }))

            imageBtnBack.setOnClickListener {
                backMainPage()
            }

            textViewCategoryName.text = mainArgs.categoryName

        }
        viewModel.fetchRecommendationGatheringData(mainArgs.categoryId.toInt())
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.recommendationData.collect {
                    when (it.plubbings.content.size) {
                        0 -> Log.d("TAG", "nothing")//HasNotDataRecycler()
                        else -> {
                            categorygridAdapter.submitList(it.plubbings.content)
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

    fun goToDetailRecruitment(plubbingId : Int) {
        val action =
            CategoryChoiceFragmentDirections.actionCategoryChoiceFragmentToRecruitmentFragment(plubbingId.toString())
        findNavController().navigate(action)
    }

    fun backMainPage(){
        //fragmentManager?.popBackStackImmediate()
    }
}