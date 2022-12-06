package com.plub.presentation.ui.home.adapter

import com.plub.presentation.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.SampleCategoryVo
import com.plub.presentation.ui.home.plubing.MainFragmentViewModel


class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.ViewHolder?>() {
    lateinit var mainCategoryItemAdapter: MainCategoryItemAdapter
    private lateinit var viewmodel: MainFragmentViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_main_category, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            val titlelist = listOf("테스트1", "테스트2", "테스트3", "테스트4", "테스트5", "테스트6", "테스트7", "테스트8")
            val image_list = listOf("테스트1", "테스트2", "테스트3", "테스트4", "테스트5", "테스트6", "테스트7", "테스트8")
            val dum_list = mutableListOf<SampleCategoryVo>()
            for(i in 0..titlelist.size - 1){
                dum_list.add(SampleCategoryVo(image_list[i], titlelist[i]))
            }
            val rv_category_item_list = itemView.findViewById<RecyclerView>(R.id.rv_main_category)
            val gridLayoutManager = GridLayoutManager(itemView.context, 4)
            rv_category_item_list.layoutManager = gridLayoutManager
            mainCategoryItemAdapter = MainCategoryItemAdapter(viewmodel)
            mainCategoryItemAdapter.submitList(dum_list)
            rv_category_item_list.adapter = mainCategoryItemAdapter


        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return 1
    }

    fun setViewModel(vm : MainFragmentViewModel){
        viewmodel = vm
    }
}