package com.plub.presentation.ui.main.home.recruitment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeDialogSuccessRecruitApplyBinding
import com.plub.presentation.util.onThrottleClick

class RecruitApplySuccessDialogFragment(private val listener : Delegate) : DialogFragment(){
    private val binding: IncludeDialogSuccessRecruitApplyBinding by lazy {
        IncludeDialogSuccessRecruitApplyBinding.inflate(layoutInflater)
    }

    interface Delegate{
        fun closeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){
        binding.apply {
            imageViewClose.onThrottleClick {
                listener.closeButtonClick()
                dismiss()
            }

            lottieThumbnail.setAnimation(R.raw.onboarding_dummy_first)
        }
    }
}