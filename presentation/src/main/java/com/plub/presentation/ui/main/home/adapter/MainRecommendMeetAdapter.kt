package com.plub.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R

class MainRecommendMeetAdapter : RecyclerView.Adapter<MainRecommendMeetAdapter.ViewHolder?>() {
    lateinit var mainRecommendAdapter: MainRecommendAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_main_recommend_meet, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
 //       val addSection : ConstraintLayout


        init {
            val titlelist = listOf("1번타이들", "2번타이틀", "3번타이틀", "4번타이틀", "5번타이틀")
            val introlist = listOf("1번소개내용","2번소개내용", "3번소개내용", "4번소개내용", "5번소개내용")
            val rv_main_list = itemView.findViewById<RecyclerView>(R.id.recycler_recommend_meet_list)
            rv_main_list.setLayoutManager(LinearLayoutManager(itemView.context))
            mainRecommendAdapter = MainRecommendAdapter(titlelist, introlist)
            rv_main_list.adapter = mainRecommendAdapter
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return 1
    }
}