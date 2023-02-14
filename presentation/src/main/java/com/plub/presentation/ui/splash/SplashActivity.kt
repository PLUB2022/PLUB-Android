package com.plub.presentation.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.plub.presentation.R
import com.plub.presentation.base.BaseActivity
import com.plub.presentation.databinding.ActivitySplashBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
BaseActivity<ActivitySplashBinding, PageState.Default, SplashViewModel>(ActivitySplashBinding::inflate){

    override val viewModel: SplashViewModel by viewModels()

    override fun initView() {

    }

    override fun initState() {
        super.initState()
    }
}