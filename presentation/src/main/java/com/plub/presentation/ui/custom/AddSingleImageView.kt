package com.plub.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewAddSingleImageBinding
import com.plub.presentation.util.GlideUtil
import java.io.File

class AddSingleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomViewAddSingleImageBinding
    var gatheringImage: File? = null
    set(value) {
        field = value
        if(value == null) {
            binding.imageViewSelectedGathering.visibility = View.GONE
            return
        }
        binding.imageViewSelectedGathering.visibility = View.VISIBLE
        GlideUtil.loadRadiusImageScaleTypeCenterCrop(context, value, binding.imageViewSelectedGathering, 6)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_add_single_image, this, true)
    }
}