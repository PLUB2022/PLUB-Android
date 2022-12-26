package com.plub.presentation.ui.home.plubing.categoryChoice

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.state.SampleHomeState
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryChoiceBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryChoiceFragment : BaseFragment<FragmentCategoryChoiceBinding, SampleHomeState, CategoryChoiceViewModel>(
    FragmentCategoryChoiceBinding::inflate
)  {
    lateinit var categorylistAdapter: MainRecommendGatheringAdapter
    override val viewModel: CategoryChoiceViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.isHaveInterest()
            //TODO 할 일
        }
    }

    override fun initState() {
        //TODO("Not yet implemented")
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
    }

    fun HasDataRecycler(){
        val rv_category_list = binding.root.findViewById<RecyclerView>(R.id.recycler_view_category_choice_list)
        rv_category_list.setLayoutManager(LinearLayoutManager(context))
        categorylistAdapter = MainRecommendGatheringAdapter()
        rv_category_list.adapter = categorylistAdapter
    }

    fun changeGridRecycler(){
        val rv_category_list = binding.root.findViewById<RecyclerView>(R.id.recycler_view_category_choice_list)
        rv_category_list.setLayoutManager(GridLayoutManager(context, 2))
        categorylistAdapter = MainRecommendGatheringAdapter()
        rv_category_list.adapter = categorylistAdapter
    }

    fun changeListRecycler(){
        val rv_category_list = binding.root.findViewById<RecyclerView>(R.id.recycler_view_category_choice_list)
        rv_category_list.setLayoutManager(LinearLayoutManager(context))
        categorylistAdapter = MainRecommendGatheringAdapter()
        rv_category_list.adapter = categorylistAdapter
    }
}