package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.databinding.BottomSheetSearchLocationBinding
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter.KakaoLocationRecyclerViewAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BottomSheetSearchLocation(
    private val okButtonClickEvent: ((data: KakaoLocationInfoDocumentVo?) -> Unit)? = null
) : BottomSheetDialogFragment() {

    private val binding: BottomSheetSearchLocationBinding by lazy {
        BottomSheetSearchLocationBinding.inflate(layoutInflater)
    }
    private val viewModel: BottomSheetSearchLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.vm = viewModel
        val pagingDataAdapter = KakaoLocationRecyclerViewAdapter { data ->
            viewModel.onClickLocationRecyclerItem(data)
        }

        binding.recyclerViewKakaoLocation.adapter = pagingDataAdapter
        binding.buttonOk.setOnClickListener {
            okButtonClickEvent?.let { method ->
                method(viewModel.uiState.value.selectedLocation)
            }
            dismiss()
        }

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

                launch {
                    viewModel.hideKeyboard.collect {
                        val inputMethodManager =
                            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(
                            binding.iconEditTextSearchLocation.editText.windowToken,
                            0
                        )
                    }
                }

                launch {
                    pagingDataAdapter.onPagesUpdatedFlow.collectLatest {
                        val currentList = pagingDataAdapter.snapshot()

                        val size =
                            if (currentList.isEmpty()) 0 else currentList[0]?.documentTotalCount
                                ?: 0
                        viewModel.upDateSearchResultCount(size)
                        viewModel.updateSearchedText()
                    }
                }
            }
        }
    }
}