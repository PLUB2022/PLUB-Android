package com.plub.presentation.ui.main.home.recruitment.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeDialogSuccessApplyBinding

class ApplySuccessDialog(
    context: Context,
    private val closeCallback: ()-> Unit
) : Dialog(context) {
    private lateinit var binding: IncludeDialogSuccessApplyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IncludeDialogSuccessApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        imageBtnClose.setOnClickListener {
            dismiss()
            closeCallback()
        }

        lottieThumbnail.setAnimation(R.raw.onboarding_dummy_first)
    }
}