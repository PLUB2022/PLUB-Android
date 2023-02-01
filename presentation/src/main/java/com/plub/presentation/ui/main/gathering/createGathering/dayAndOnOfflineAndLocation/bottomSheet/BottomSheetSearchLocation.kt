package com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetSearchLocationBinding
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter.KakaoLocationRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BottomSheetSearchLocation :
    BaseBottomSheetFragment<BottomSheetSearchLocationBinding, CreateGatheringKakaoLocationBottomSheetPageState, BottomSheetSearchLocationViewModel>(
        BottomSheetSearchLocationBinding::inflate
    ) {
    companion object {

        private const val POSITION_TOP = 0
        fun newInstance(
            onConfirmButtonClick: (data: KakaoLocationInfoDocumentVo?) -> Unit
        ) = BottomSheetSearchLocation().apply {
            this.onConfirmButtonClick = onConfirmButtonClick
        }
    }

    override val viewModel: BottomSheetSearchLocationViewModel by viewModels()

    private var onConfirmButtonClick: ((data: KakaoLocationInfoDocumentVo?) -> Unit)? = null

    private val pagingDataAdapter: KakaoLocationRecyclerViewAdapter by lazy {
        KakaoLocationRecyclerViewAdapter(object : KakaoLocationRecyclerViewAdapter.Delegate {
            override fun onClickItem(data: KakaoLocationInfoDocumentVo, position:Int) {
                viewModel.onClickLocationRecyclerItem(data, position)
            }

            override val selectedVo: KakaoLocationInfoDocumentVo?
                get() = viewModel.uiState.value.selectedLocation
        })
    }

    override fun initView() {
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            recyclerViewKakaoLocation.adapter = pagingDataAdapter
            iconEditTextSearchLocation.editText.apply {
                setOnEditorActionListener(object : TextView.OnEditorActionListener {
                    override fun onEditorAction(
                        textView: TextView?,
                        actionId: Int,
                        keyEvent: KeyEvent?
                    ): Boolean {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            viewModel.onClickKeyboardSearch(text.toString())
                            return true
                        }
                        return false
                    }
                })
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.pagingData.collectLatest {
                    pagingDataAdapter.submitData(it)
                }
            }

            launch {
                pagingDataAdapter.onPagesUpdatedFlow.collect {
                    viewModel.onPageUpdated(pagingDataAdapter.snapshot())
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as KakaoLocationEvent)
                }
            }
        }
    }


    private fun inspectEventFlow(event: KakaoLocationEvent) {
        when (event) {
            is KakaoLocationEvent.ConfirmDialog -> onConfirmDialog(event.data)
            is KakaoLocationEvent.HideKeyboard -> hideKeyboard()
            is KakaoLocationEvent.ScrollToTop -> scrollToTop()
            is KakaoLocationEvent.NotifyItemChanged -> notifyChanged(event.position)
        }
    }

    private fun onConfirmDialog(data: KakaoLocationInfoDocumentVo?) {
        onConfirmButtonClick?.let {
            it.invoke(data)
            dismiss()
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.iconEditTextSearchLocation.editText.windowToken, 0)
    }

    private fun scrollToTop() {
        binding.recyclerViewKakaoLocation.scrollToPosition(POSITION_TOP)
    }

    private fun notifyChanged(position:Int) {
        pagingDataAdapter.notifyItemChanged(position)
    }

}