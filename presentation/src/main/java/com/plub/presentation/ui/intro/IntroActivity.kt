package com.plub.presentation.ui.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plub.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
}