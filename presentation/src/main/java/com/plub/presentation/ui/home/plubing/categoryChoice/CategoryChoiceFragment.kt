package com.plub.presentation.ui.home.plubing.categoryChoice

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.state.SampleHomeState
import com.plub.presentation.ui.home.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGridAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryChoiceFragment : BaseFragment<FragmentCategoryChoiceBinding, SampleHomeState, CategoryChoiceViewModel>(
    FragmentCategoryChoiceBinding::inflate
)  {
    lateinit var categorylistAdapter: MainRecommendAdapter
    lateinit var categorygridAdapter: MainRecommendGridAdapter
    override val viewModel: CategoryChoiceViewModel by viewModels()
    val dum_list = mutableListOf<GatheringItemVo>()

    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.isHaveInterest()
            //TODO 할 일
        }
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.testHomeData.collect {
                    if (it.equals("")) {
                        //HasNotDataRecycler()
                        Log.d("CategoryChoiceFragmentTag", "실패")
                    } else if (it.equals("에러")) {
                        Log.d("CategoryChoiceFragmentTag", "에러난거임")
                    } else if (it.equals("로딩")) {
                        Log.d("CategoryChoiceFragmentTag", "로딩중임")
                    } else {
                        Log.d("CategoryChoiceFragmentTag", "성공")
                        val titlelist = listOf("1번타이들", "2번타이틀", "3번타이틀", "4번타이틀", "5번타이틀")
                        val introlist = listOf("1번소개내용","2번소개내용", "3번소개내용", "4번소개내용", "5번소개내용")
                        for(i in 0..titlelist.size - 1){
                            dum_list.add(GatheringItemVo("", titlelist[i], introlist[i]))
                        }
                        HasDataRecycler()
                    }
                }
            }

        }
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.switchList.collect{
                    when(it){
                        "그리드" -> changeGridRecycler()
                        "리스트" -> changeListRecycler()
                    }
                }

            }
        }
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.goToDetailRecruitmentFragment.collect{
                    goToDetailRecruitment()
                }

            }
        }

    }

    fun HasDataRecycler(){
        val rv_category_list = binding.root.findViewById<RecyclerView>(R.id.recycler_view_category_choice_list)
        rv_category_list.setLayoutManager(LinearLayoutManager(context))
        categorylistAdapter = MainRecommendAdapter()
        categorylistAdapter.setViewmodel(viewModel)
        categorylistAdapter.submitList(dum_list)
        rv_category_list.adapter = categorylistAdapter
    }

    fun changeGridRecycler(){
        val rv_category_list = binding.root.findViewById<RecyclerView>(R.id.recycler_view_category_choice_list)
        rv_category_list.setLayoutManager(GridLayoutManager(context, 2))
        categorygridAdapter = MainRecommendGridAdapter()
        categorygridAdapter.submitList(dum_list)
        rv_category_list.adapter = categorygridAdapter
    }

    fun changeListRecycler(){
        val rv_category_list = binding.root.findViewById<RecyclerView>(R.id.recycler_view_category_choice_list)
        rv_category_list.setLayoutManager(LinearLayoutManager(context))
        categorylistAdapter = MainRecommendAdapter()
        categorylistAdapter.setViewmodel(viewModel)
        categorylistAdapter.submitList(dum_list)
        rv_category_list.adapter = categorylistAdapter
    }

    fun goToDetailRecruitment(){
        val action = CategoryChoiceFragmentDirections.actionCategoryChoiceFragmentToRecruitmentFragment()
        findNavController().navigate(action)
    }
}