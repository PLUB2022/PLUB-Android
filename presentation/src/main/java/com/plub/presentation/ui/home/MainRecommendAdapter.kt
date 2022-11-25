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

class MainRecommendAdapter(title : List<String>, intro : List<String>) : RecyclerView.Adapter<MainRecommendAdapter.ViewHolder?>() {

    var titles : List<String> = title
    var intros : List<String> = intro
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_recommend_meet_list_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_title = itemView.findViewById<TextView>(R.id.tv_meet_title)
        val tv_intro_text = itemView.findViewById<TextView>(R.id.tv_meet_oneline_introduce)
        fun bind(title : String, intro : String){
            tv_title.text = title
            tv_intro_text.text = intro
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(titles[position], intros[position])
    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return titles.size
    }
}