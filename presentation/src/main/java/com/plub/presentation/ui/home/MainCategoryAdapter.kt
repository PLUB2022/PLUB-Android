package com.plub.presentation.ui.home

import com.plub.presentation.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_main_category, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn_art : ImageButton
        val tv_art : TextView

        init {
            btn_art = itemView.findViewById(R.id.btn_art_category)
            tv_art = itemView.findViewById(R.id.tv_art_category)

            btn_art.setOnClickListener {
                tv_art.text = "예술 클릭"
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