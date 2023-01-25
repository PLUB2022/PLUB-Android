package com.plub.presentation.ui.home.plubing.main

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMainBinding
import com.plub.presentation.state.MainPageState
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainPageState, MainFragmentViewModel>(
    FragmentMainBinding::inflate)
{
    override val viewModel: MainFragmentViewModel by viewModels()

    private val mainCategoryAdapter: MainCategoryAdapter by lazy {
        MainCategoryAdapter(object : MainCategoryAdapter.MainCategoryDelegate {
            override fun onClick(categoryId: Int) {
                goToCategoryChoice(categoryId)
            }
        })
    }

    private val recommendationListAdapter : MainRecommendGatheringAdapter by lazy {
        MainRecommendGatheringAdapter(object : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate {
            override fun onClick(plubbingId: Int) {
                goToRecruitmentFragment(plubbingId)
            }
        })
    }

    private val mainRecommendMeetXAdapter : MainRecommendGatheringXAdapter by lazy {
        MainRecommendGatheringXAdapter(object : MainRecommendGatheringXAdapter.MainRecommendGatheringXDelegate {
            //TODO 리스너 달기
        })
    }

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
        }
        viewModel.fetchMainPageData()
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.categoryData.collect{
                    mainCategoryAdapter.submitList(arrayListOf(it.data))
                }
            }

            launch {
                viewModel.recommendationData.collect{
                    when(it.plubbings.content.size){
                        0 -> HasNotDataRecycler()
                        else -> HasDataRecycler(it)
                    }
                }
            }

        }
    }

    fun goToCategoryChoice(categoryId : Int){
        val action = MainFragmentDirections.actionMainToCategoryChoice(categoryId.toString())
        findNavController().navigate(action)
    }

    fun goToRecruitmentFragment(plubbingId : Int){
        val action = MainFragmentDirections.actionMainFragmentToRecruitmentFragment(plubbingId.toString())
        findNavController().navigate(action)
    }

    fun HasDataRecycler(data : RecommendationGatheringResponseVo){
        val mConcatAdapter = ConcatAdapter()
        recommendationListAdapter.submitList(arrayListOf(data.plubbings))
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(recommendationListAdapter)
        binding.recyclerViewMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

    fun HasNotDataRecycler(){
        val mConcatAdapter = ConcatAdapter()
        mainRecommendMeetXAdapter.submitList(arrayListOf(0))
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetXAdapter)
        binding.recyclerViewMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

}