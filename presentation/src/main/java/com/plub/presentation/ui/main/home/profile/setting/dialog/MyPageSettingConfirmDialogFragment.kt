package com.plub.presentation.ui.main.home.profile.setting.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.plub.presentation.databinding.FragmentMyPageAgainQuestionDialogBinding
import com.plub.presentation.util.onThrottleClick

class MyPageSettingConfirmDialogFragment(private val listener : Delegate) : DialogFragment(){
    private val binding: FragmentMyPageAgainQuestionDialogBinding by lazy {
        FragmentMyPageAgainQuestionDialogBinding.inflate(layoutInflater)
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

            buttonChangeMyInfo.onThrottleClick {
                listener.onYesButtonClick()
                dismiss()
            }
        }
    }
}