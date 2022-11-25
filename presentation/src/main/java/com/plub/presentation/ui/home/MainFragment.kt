package com.plub.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.UiState
import com.plub.domain.model.state.SampleHomeState
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentLoginBinding
import com.plub.presentation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, SampleHomeState, MainFragmentViewModel>(FragmentMainBinding::inflate
)  {
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    lateinit var mainRecommendMeetXAdapter: MainRecommendMeetXAdapter

    override val viewModel: MainFragmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            initRecycler()
            //TODO 할 일
        }
    }

    override fun initState() {
        //TODO("Not yet implemented")
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.testHomeData.collect {
                    Log.d("TATATATATA", it)
                }
            }

        }
    }

    fun initRecycler(){

        viewModel.isHaveInterest()
        val rv_main = binding.root.findViewById<RecyclerView>(R.id.rv_main_page)
        rv_main.setLayoutManager(LinearLayoutManager(context))
        mainCategoryAdapter = MainCategoryAdapter()
        mainRecommendMeetXAdapter = MainRecommendMeetXAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetXAdapter)
        rv_main.adapter = mConcatAdapter
    }
}