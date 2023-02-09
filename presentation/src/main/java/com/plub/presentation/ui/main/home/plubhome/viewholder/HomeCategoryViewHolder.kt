package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemHomeCategoryBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeAdapter
import com.plub.presentation.util.GlideUtil

class HomeCategoryViewHolder (
    private val binding : IncludeItemHomeCategoryBinding,
    private val listener : HomeAdapter.HomeDelegate,
) : RecyclerView.ViewHolder(binding.root){
    private var voList : List<CategoriesDataResponseVo> = emptyList()

    companion object{
        private val FIRST = 0
        private val SECOND = 1
        private val THIRD = 2
        private val FOURTH = 3
        private val FIFTH = 4
        private val SIXTH = 5
        private val SEVENTH = 6
        private val EIGHTH = 7
    }
    init {
        binding.apply {
            constraintLayoutFirstCategoryTouch.setOnClickListener {
                onCategoryClick(FIRST)
            }
            constraintLayoutSecondCategoryTouch.setOnClickListener {
                onCategoryClick(SECOND)
            }
            constraintLayoutThirdCategoryTouch.setOnClickListener {
                onCategoryClick(THIRD)
            }
            constraintLayoutFourthCategoryTouch.setOnClickListener {
                onCategoryClick(FOURTH)
            }
            constraintLayoutFifthCategoryTouch.setOnClickListener {
                onCategoryClick(FIFTH)
            }
            constraintLayoutSixthCategoryTouch.setOnClickListener {
                onCategoryClick(SIXTH)
            }
            constraintLayoutSeventhCategoryTouch.setOnClickListener {
                onCategoryClick(SEVENTH)
            }
            constraintLayoutEighthCategoryTouch.setOnClickListener {
                onCategoryClick(EIGHTH)
            }
        }
    }

    private fun onCategoryClick(position : Int){
        listener.onCategoryClick(voList[position].id, voList[position].name)
    }

    fun bind(item: List<CategoriesDataResponseVo>) {
        voList = item
        binding.apply {
            GlideUtil.loadImage(root.context, item[FIRST].icon, imageViewFirstCategory)
            textViewTitleFirstCategory.text = item[FIRST].name

            GlideUtil.loadImage(root.context, item[SECOND].icon, imageViewSecondCategory)
            textViewTitleSecondCategory.text = item[SECOND].name

            GlideUtil.loadImage(root.context, item[THIRD].icon, imageViewThirdCategory)
            textViewTitleThirdCategory.text = item[THIRD].name

            GlideUtil.loadImage(root.context, item[FOURTH].icon, imageViewFourthCategory)
            textViewTitleFourthCategory.text = item[FOURTH].name

            GlideUtil.loadImage(root.context, item[FIFTH].icon, imageViewFifthCategory)
            textViewTitleFifthCategory.text = item[FIFTH].name

            GlideUtil.loadImage(root.context, item[SIXTH].icon, imageViewSixthCategory)
            textViewTitleSixthCategory.text = item[SIXTH].name

            GlideUtil.loadImage(root.context, item[SEVENTH].icon, imageViewSeventhCategory)
            textViewTitleSeventhCategory.text = item[SEVENTH].name

            GlideUtil.loadImage(root.context, item[EIGHTH].icon, imageViewEighthCategory)
            textViewTitleEighthCategory.text = item[EIGHTH].name
        }
    }
}