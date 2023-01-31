package com.plub.presentation.ui.main.home.adapter

import com.plub.presentation.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.ViewHolder?>() {
    lateinit var mainCategoryItemAdapter: MainCategoryItemAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_main_category, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            val titlelist = listOf("테스트1", "테스트2", "테스트3", "테스트4", "테스트5", "테스트6", "테스트7", "테스트8")
            val image_list = listOf("테스트1", "테스트2", "테스트3", "테스트4", "테스트5", "테스트6", "테스트7", "테스트8")
            val rv_category_item_list = itemView.findViewById<RecyclerView>(R.id.rv_main_category)
            val gridLayoutManager = GridLayoutManager(itemView.context, 4)
            rv_category_item_list.layoutManager = gridLayoutManager
            mainCategoryItemAdapter = MainCategoryItemAdapter(image_list, titlelist)
            rv_category_item_list.adapter = mainCategoryItemAdapter


        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return 1
    }
}