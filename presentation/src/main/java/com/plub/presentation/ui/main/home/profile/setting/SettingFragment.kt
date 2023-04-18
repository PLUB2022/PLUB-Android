package com.plub.presentation.ui.main.home.profile.setting

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentSettingBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment :
    BaseTestFragment<FragmentSettingBinding, SettingState, SettingViewModel>(
        FragmentSettingBinding::inflate
    ) {

    companion object{
        const val TEXT_MAILTO = "mailto"
    }
    override val viewModel: SettingViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as SettingEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SettingEvent) {
        when(event){
            is SettingEvent.GoToBack -> {findNavController().popBackStack()}
            is SettingEvent.GoToEmail -> sendToEmail()
            is SettingEvent.GoToFAQ -> goToFAQ()
            is SettingEvent.GoToNotice -> goToNotice()
        }
    }

    private fun sendToEmail(){
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            TEXT_MAILTO, context?.getString(R.string.plub_emil) ?: "", null)
        )
        startActivity(intent)
    }

    private fun goToFAQ(){

    }

    private fun goToNotice(){

    }
}