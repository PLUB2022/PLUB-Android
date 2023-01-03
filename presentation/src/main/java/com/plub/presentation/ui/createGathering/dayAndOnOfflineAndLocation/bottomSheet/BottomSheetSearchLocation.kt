package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.presentation.databinding.BottomSheetSearchLocationBinding
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter.KakaoLocationRecyclerViewAdapter
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


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
        val pagingDataAdapter = KakaoLocationRecyclerViewAdapter { text ->
            viewModel.onClickLocationRecyclerViewItem(text)
        }
        binding.recyclerViewKakaoLocation.adapter = pagingDataAdapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.locationData.collectLatest { flow ->
                        binding.recyclerViewKakaoLocation.scrollToPosition(0)
                        flow.collectLatest { pageData ->
                            pagingDataAdapter.submitData(pageData)
                        }
                    }
                }
            }
        }

        dialog.setContentView(binding.root)
    }
}