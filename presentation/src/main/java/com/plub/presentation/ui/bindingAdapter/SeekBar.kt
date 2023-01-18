package com.plub.presentation.ui.bindingAdapter

import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.plub.presentation.util.px


@BindingAdapter("updateSeekBarProgressAndPosition")
fun SeekBar.updateSeekBarProgressAndPosition(method: (progress: Int, position: Float) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
            seekBar?.let { seekBar ->
                method(seekBar.progress, (((seekBar.width - (seekBar.paddingStart + seekBar.paddingEnd))/seekBar.max) * seekBar.progress).toFloat())
            }
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    })
}