package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutMainCategoryBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainCategoryParentViewHoler
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter


class MainCategoryAdapter(private val listener: Delegate) : ListAdapter<CategoryListDataResponseVo, RecyclerView.ViewHolder>(
    MainCategoryDiffCallBack()
){
    private val subListenerList: MutableSet<HobbiesAdapter.SubListener> = mutableSetOf()

    interface Delegate {
//          val categoryList:List<CategoriesDataResponseVo>
//        fun onClickExpand(hobbyId: Int)
//        fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo)
//        fun onClickLatePick()
    }

    interface SubListener {
        fun onNotifySubItemChange(parentId: Int, subId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainCategoryParentViewHoler -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemLayoutMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCategoryParentViewHoler(binding, listener)
    }

}

class MainCategoryDiffCallBack : DiffUtil.ItemCallback<CategoryListDataResponseVo>() {
    override fun areItemsTheSame(oldItem:CategoryListDataResponseVo, newItem: CategoryListDataResponseVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: CategoryListDataResponseVo, newItem: CategoryListDataResponseVo): Boolean = oldItem == newItem
}

