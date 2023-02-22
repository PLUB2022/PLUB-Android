package com.plub.presentation.ui.main.home.plubing

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMainBinding
import com.plub.presentation.ui.main.home.adapter.MainCategoryAdapter
import com.plub.presentation.ui.main.home.adapter.MainRecommendMeetAdapter
import com.plub.presentation.ui.main.home.adapter.MainRecommendMeetXAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, SampleHomeState, MainFragmentViewModel>(FragmentMainBinding::inflate
)  {
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    lateinit var mainRecommendMeetXAdapter: MainRecommendMeetXAdapter
    lateinit var mainRecommendMeetadapter: MainRecommendMeetAdapter

    override val viewModel: MainFragmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.isHaveInterest()
            //TODO 할 일

            //TODO REMOVE 메인 툴바 클릭 시 임시로 모임 생성 페이지 이동 구현함
            buttonCreateGathering.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainToCreateGathering())
            }
            buttonSearching.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainToSearching())
            }
            buttonBookmark.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainToBookmark())
            }
            buttonModifyGathering.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainToModifyGathering())
            }
            buttonPlubingMain.setOnClickListener {
                val plubingId = 1
                findNavController().navigate(MainFragmentDirections.actionMainToPlubingMain(plubingId))
            }
            buttonPlubingSchedule.setOnClickListener {
                val plubingId = 1
                findNavController().navigate(MainFragmentDirections.actionMainToPlubingSchedule(plubingId, "요란한 한줄"))
            }
        }
    }

    override fun initStates() {
        //TODO("Not yet implemented")
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
    }

    fun HasDataRecycler(){

        val rv_main = binding.root.findViewById<RecyclerView>(R.id.rv_main_page)
        rv_main.setLayoutManager(LinearLayoutManager(context))
        mainCategoryAdapter = MainCategoryAdapter()
        mainRecommendMeetadapter = MainRecommendMeetAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetadapter)
        rv_main.adapter = mConcatAdapter
    }

    fun HasNotDataRecycler(){

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