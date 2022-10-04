package com.plub.presentation.ui.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plub.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
    }
}