package com.plub.presentation.ui.main.profile.recruiting.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.plub.presentation.databinding.IncludeItemDialogMyPageAgainApproveBinding
import com.plub.presentation.util.onThrottleClick

class MyPageRecruitingAgainApproveDialogFragment(private val listener : Delegate) : DialogFragment(){
    private val binding: IncludeItemDialogMyPageAgainApproveBinding by lazy {
        IncludeItemDialogMyPageAgainApproveBinding.inflate(layoutInflater)
    }

    interface Delegate{
        fun onYesButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCancelable(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){
        binding.apply {
            buttonCancel.onThrottleClick {
                dismiss()
            }

            buttonApprove.onThrottleClick {
                listener.onYesButtonClick()
                dismiss()
            }
        }
    }
}