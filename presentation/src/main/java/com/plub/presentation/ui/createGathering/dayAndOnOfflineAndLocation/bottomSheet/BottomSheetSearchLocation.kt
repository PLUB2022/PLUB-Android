package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.presentation.databinding.BottomSheetSearchLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetSearchLocation : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetSearchLocationBinding
    private val viewModel: BottomSheetSearchLocationViewModel by viewModels()

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        binding = BottomSheetSearchLocationBinding.inflate(LayoutInflater.from(context))
        binding.lifecycleOwner = this

        binding.vm = viewModel

        dialog.setContentView(binding.root)
    }
}