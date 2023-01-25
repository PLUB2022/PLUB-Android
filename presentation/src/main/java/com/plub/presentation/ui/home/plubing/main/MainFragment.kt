package com.plub.presentation.ui.home.plubing.main

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
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
    lateinit var mainRecommendMeetXAdapter: MainRecommendGatheringXAdapter
    override val viewModel: MainFragmentViewModel by viewModels()

    private val categorylistAdapter: MainCategoryAdapter by lazy {
        MainCategoryAdapter(object : MainCategoryAdapter.Delegate {
            //TODO 리스너 달기
        })
    }

    private val recommendationListAdapter : MainRecommendGatheringAdapter by lazy {
        MainRecommendGatheringAdapter(object : MainRecommendGatheringAdapter.Delegate {
            //TODO 리스너 달기
        })
    }

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
        }
        viewModel.initMainPage()
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    categorylistAdapter.submitList(arrayListOf(it.categoryVo.data))
                }
            }

            launch {
                viewModel.recommendationData.collect{
                    Log.d("테스트 테그", it.plubbings.toString())
                    when(it.plubbings.content.size){
                        0 -> HasNotDataRecycler()
                        else -> HasDataRecycler(it)
                    }
                }
            }

            launch {
                viewModel.goToCategoryChoiceFragment.collect {
                    goToCategoryChoice()
                }
            }

        }
    }

    fun goToCategoryChoice(){
        val action = MainFragmentDirections.actionMainToCategoryChoice()
        findNavController().navigate(action)
    }

    fun HasDataRecycler(data : RecommendationGatheringResponseVo){
        val mConcatAdapter = ConcatAdapter()
        recommendationListAdapter.submitList(arrayListOf(data.plubbings))
        mConcatAdapter.addAdapter(categorylistAdapter)
        mConcatAdapter.addAdapter(recommendationListAdapter)
        binding.rvMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

    fun HasNotDataRecycler(){
        mainRecommendMeetXAdapter = MainRecommendGatheringXAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(categorylistAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetXAdapter)
        binding.rvMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

}