package com.plub.presentation.ui.home.plubing.main

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMainBinding
import com.plub.presentation.state.MainPageState
import com.plub.presentation.ui.common.VerticalSpaceDecoration
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter
import com.plub.presentation.ui.sign.hobbies.HobbiesFragment
import com.plub.presentation.util.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainPageState, MainFragmentViewModel>(
    FragmentMainBinding::inflate)
{
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    lateinit var mainRecommendMeetXAdapter: MainRecommendGatheringXAdapter
    lateinit var mainRecommendMeetadapter: MainRecommendGatheringAdapter
    private lateinit var categoriesData : List<CategoriesDataResponseVo>

    override val viewModel: MainFragmentViewModel by viewModels()

    private val listAdapter: MainCategoryAdapter by lazy {
        MainCategoryAdapter(object : MainCategoryAdapter.Delegate {
            override val categoryList: List<CategoriesDataResponseVo>
                get() = viewModel.uiState.value.categoryVo.data.categories

//            override fun onClickExpand(hobbyId: Int) {
//                viewModel.onClickExpand(hobbyId)
//            }
//
//            override fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
//                viewModel.onClickSubHobby(isClicked, selectedHobbyVo)
//            }
//
//            override fun onClickLatePick() {
//                viewModel.onClickLatePick()
//            }
        })
    }

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
        }
        viewModel.isHaveInterest()
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    listAdapter.submitList(arrayListOf(it.categoryVo.data))
                }
            }

            launch {
                viewModel.recommendationData.collect{
                    Log.d("테스트 테그", it.plubbings.toString())
                    when(it.plubbings.content.size){
                        0 -> HasNotDataRecycler()
                        else -> HasDataRecycler()
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

    fun HasDataRecycler(){
        mainRecommendMeetadapter = MainRecommendGatheringAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(listAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetadapter)
        binding.rvMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

    fun HasNotDataRecycler(){
        mainRecommendMeetXAdapter = MainRecommendGatheringXAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(listAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetXAdapter)
        binding.rvMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

}