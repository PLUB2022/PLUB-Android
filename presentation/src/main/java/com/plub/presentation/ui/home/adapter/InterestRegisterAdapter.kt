package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R

//TODO List타입 카테고리 데이터 모델로 바꿔서 사용.
class InterestRegisterAdapter(private var mlist : List<String>) : RecyclerView.Adapter<InterestRegisterAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.include_item_interest_register, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val btn_art : ImageButton
//        val tv_art : TextView

        init {
//            btn_art = itemView.findViewById(R.id.btn_art_category)
//            tv_art = itemView.findViewById(R.id.tv_art_category)
//
//            btn_art.setOnClickListener {
//                tv_art.text = "예술 클릭"
//            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return mlist.size
    }
}