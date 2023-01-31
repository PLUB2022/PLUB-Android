package com.plub.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R

class MainCategoryItemAdapter(imageRes : List<String>, title : List<String>) : RecyclerView.Adapter<MainCategoryItemAdapter.ViewHolder?>() {

    var image_ress : List<String> = imageRes
    var titles : List<String> = title
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_main_category, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_resos = itemView.findViewById<ImageView>(R.id.icon_category)
        val title_text = itemView.findViewById<TextView>(R.id.tv_title_category)

        fun bind(img_res : String, title : String){
           title_text.text = title
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(image_ress[position], titles[position])
    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return titles.size
    }
}