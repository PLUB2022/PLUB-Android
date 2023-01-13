package com.plub.presentation.ui.home.plubing.main

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.state.SampleHomeState
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMainBinding
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, SampleHomeState, MainFragmentViewModel>(
    FragmentMainBinding::inflate)
{
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    lateinit var mainRecommendMeetXAdapter: MainRecommendGatheringXAdapter
    lateinit var mainRecommendMeetadapter: MainRecommendGatheringAdapter

    override val viewModel: MainFragmentViewModel by viewModels()

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
                    if(it.equals("")){
                        HasNotDataRecycler()
                    }
                    else if(it.equals("에러")){
                        Log.d("MainFragmentTag", "에러난거임")
                    }
                    else if(it.equals("로딩")){
                        Log.d("MainFragmentTag", "로딩중임")
                    }
                    else{
                        HasDataRecycler()
                    }
                }
            }

        }
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.goToCategoryChoiceFragment.collect {
                goToCategoryChoice()
            }
        }
    }

    fun goToCategoryChoice(){
        val action = MainFragmentDirections.actionMainToCategoryChoice()
        findNavController().navigate(action)
    }

    fun HasDataRecycler(){

        val rv_main = binding.root.findViewById<RecyclerView>(R.id.rv_main_page)
        rv_main.setLayoutManager(LinearLayoutManager(context))
        mainCategoryAdapter = MainCategoryAdapter()
        mainCategoryAdapter.setViewModel(viewModel)
        mainRecommendMeetadapter = MainRecommendGatheringAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetadapter)
        rv_main.adapter = mConcatAdapter
    }

    fun HasNotDataRecycler(){

        val rv_main = binding.root.findViewById<RecyclerView>(R.id.rv_main_page)
        rv_main.setLayoutManager(LinearLayoutManager(context))
        mainCategoryAdapter = MainCategoryAdapter()
        mainRecommendMeetXAdapter = MainRecommendGatheringXAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetXAdapter)
        rv_main.adapter = mConcatAdapter
    }
}