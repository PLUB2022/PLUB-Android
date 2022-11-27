package com.plub.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R


class MainFragment : Fragment() {
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    lateinit var mainRecommendMeetXAdapter: MainRecommendMeetXAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
    }

    fun initRecycler(view : View){
        val rv_main = view.findViewById<RecyclerView>(R.id.rv_main_page)
        rv_main.setLayoutManager(LinearLayoutManager(context))
        mainCategoryAdapter = MainCategoryAdapter()
        mainRecommendMeetXAdapter = MainRecommendMeetXAdapter()
        val mConcatAdapter = ConcatAdapter()
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(mainRecommendMeetXAdapter)
        rv_main.adapter = mConcatAdapter
    }
}