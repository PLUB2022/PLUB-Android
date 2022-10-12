package com.plub.presentation.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R

class MainRecommendMeetXAdapter : RecyclerView.Adapter<MainRecommendMeetXAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_main_recommend_meet_no_chocie, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addSection : ConstraintLayout


        init {
            addSection = itemView.findViewById(R.id.group_407)

            addSection.setOnClickListener {
                Log.d("TAG", "관심사 추가 클릭")
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return 1
    }
}