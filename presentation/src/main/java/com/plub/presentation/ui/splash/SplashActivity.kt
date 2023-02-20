package com.plub.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import com.plub.presentation.base.BaseActivity
import com.plub.presentation.databinding.ActivitySplashBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.MainActivity
import com.plub.presentation.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
BaseActivity<ActivitySplashBinding, PageState.Default, SplashViewModel>(ActivitySplashBinding::inflate){

    override val viewModel: SplashViewModel by viewModels()

    override fun initView() {
        viewModel.fetchMyInfo()
    }

    override fun initState() {
        super.initState()

        repeatOnStarted {
            launch {
                viewModel.eventFlow.collect { event ->
                    when(event) {
                        is SplashEvent.GoToMain -> {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        }

                        is SplashEvent.GoToSignUp -> {
                            startActivity(Intent(this@SplashActivity, SignActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }
}