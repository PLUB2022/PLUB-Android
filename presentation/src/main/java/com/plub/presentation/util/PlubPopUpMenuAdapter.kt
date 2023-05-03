package com.plub.presentation.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.plub.presentation.R
import com.skydoves.powermenu.MenuBaseAdapter

class PlubPopUpMenuAdapter : MenuBaseAdapter<String?>() {
    override fun getView(index: Int, view: View?, viewGroup: ViewGroup): View {
        var view: View? = view
        val context: Context = viewGroup.context
        if (view == null) {
            val inflater: LayoutInflater = LayoutInflater.from(context)!!
            view = inflater.inflate(R.layout.item_plub_pop_up, viewGroup, false)
        }

        val item = getItem(index) as String
        view?.findViewById<TextView>(R.id.item_title)?.apply {
            text = item
        }

        return super.getView(index, view, viewGroup)
    }

    override fun setSelectedPosition(position: Int) {
        notifyDataSetChanged()
    }
}