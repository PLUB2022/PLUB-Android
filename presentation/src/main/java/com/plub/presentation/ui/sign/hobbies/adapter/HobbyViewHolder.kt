package com.plub.presentation.ui.sign.hobbies.adapter

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemHobbyBinding
import com.plub.presentation.ui.common.GridSpaceDecoration
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px

class HobbyViewHolder(
    private val binding: IncludeItemHobbyBinding,
    private val listener: HobbiesAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root), HobbiesAdapter.SubListener {

    companion object {
        private const val TOTAL_SPAN_SIZE = 4
        private const val ITEM_SPAN_SIZE = 1
        private const val ITEM_SPACE = 8
    }

    private var vo: HobbyVo? = null
    private val listAdapter = SubHobbiesAdapter(listener)

    init {
        binding.imageViewExpand.setOnClickListener {
            vo?.let {
                listener.onClickExpand(it.id)
            }
        }

        binding.recyclerViewSubHobbies.apply {
            layoutManager = GridLayoutManager(context,TOTAL_SPAN_SIZE).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return ITEM_SPAN_SIZE
                    }
                }
            }
            addItemDecoration(GridSpaceDecoration(TOTAL_SPAN_SIZE, ITEM_SPACE.px, false))
            adapter = listAdapter
        }
    }

    fun bind(item: HobbyVo) {
        vo = item
        binding.apply {
            GlideUtil.loadImage(root.context, item.icon, imageViewIcon)
            textViewName.text = item.name
            val expandImageRes = if (item.isExpand) R.drawable.ic_arrow_over else R.drawable.ic_arrow_under
            imageViewExpand.setImageResource(expandImageRes)
            recyclerViewSubHobbies.visibility = if (item.isExpand) View.VISIBLE else View.GONE
            listAdapter.submitList(item.subHobbies)
        }
    }

    override fun onNotifySubItemChange(parentId: Int, subId: Int) {
        vo?.let {
            if(parentId != it.id) return
            listAdapter.updateOnClick(subId)
        }
    }
}