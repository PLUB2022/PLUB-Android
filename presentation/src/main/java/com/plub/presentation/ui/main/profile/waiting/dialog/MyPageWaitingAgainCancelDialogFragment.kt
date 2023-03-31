package com.plub.presentation.ui.main.profile.waiting.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.plub.presentation.databinding.IncludeItemDialogMyPageCancelBinding
import com.plub.presentation.util.onThrottleClick

class MyPageWaitingAgainCancelDialogFragment(private val listener : Delegate) : DialogFragment(){
    private val binding: IncludeItemDialogMyPageCancelBinding by lazy {
        IncludeItemDialogMyPageCancelBinding.inflate(layoutInflater)
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
            buttonNo.onThrottleClick {
                dismiss()
            }

            buttonYes.onThrottleClick {
                listener.onYesButtonClick()
                dismiss()
            }
        }
    }
}