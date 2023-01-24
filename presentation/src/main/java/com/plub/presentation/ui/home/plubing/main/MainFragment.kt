package com.plub.presentation.ui.home.plubing.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMainBinding
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.home.plubing.main.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, PageState.Default, MainFragmentViewModel>(
    FragmentMainBinding::inflate)
{
    override val viewModel: MainFragmentViewModel by viewModels()

    private val mainCategoryAdapter: MainCategoryAdapter by lazy {
        MainCategoryAdapter(object : MainCategoryAdapter.MainCategoryDelegate {
            override fun onClick(categoryId: Int, categoryName: String) {
                goToCategoryChoice(categoryId, categoryName)
            }
        })
    }

    private val recommendationListAdapter : MainRecommendGatheringAdapter by lazy {
        MainRecommendGatheringAdapter(object : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate {
            override fun onClickGoRecruitDetail(plubbingId: Int) {
                goToRecruitmentFragment(plubbingId)
            }

            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }
        })
    }

    private val mainRecommendMeetXAdapter : MainRecommendGatheringXAdapter by lazy {
        MainRecommendGatheringXAdapter(object : MainRecommendGatheringXAdapter.MainRecommendGatheringXDelegate {
            override fun onClick() {
                goToRegisterInterest()
            }
        })
    }

    override fun initView() {

        binding.apply {
            vm = viewModel
            toolbarMain.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainToCreateGathering())
            }

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
                    when(it.content.size){
                        0 -> HasNotDataRecycler()
                        else -> HasDataRecycler(it)
                    }
                }
            }

        }
    }

    fun goToCategoryChoice(categoryId : Int, categoryName : String){
        val action = MainFragmentDirections.actionMainToCategoryChoice(categoryId.toString(), categoryName)
        findNavController().navigate(action)
    }

    fun goToRecruitmentFragment(plubbingId : Int){
        val action = MainFragmentDirections.actionMainFragmentToRecruitmentFragment(plubbingId.toString())
        findNavController().navigate(action)
    }

    fun HasDataRecycler(data : RecommendationGatheringResponseVo){
        val mConcatAdapter = ConcatAdapter()
        recommendationListAdapter.submitList(arrayListOf(data))
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

    fun goToRegisterInterest(){
        val action = MainFragmentDirections.actionMainFragmentToRegisterInterestFragment()
        findNavController().navigate(action)
    }

}