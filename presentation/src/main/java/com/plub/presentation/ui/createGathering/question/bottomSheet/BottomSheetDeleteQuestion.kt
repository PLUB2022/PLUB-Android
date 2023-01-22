package com.plub.presentation.ui.createGathering.question.bottomSheet

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.databinding.BottomSheetDeleteQuestionBinding
import com.plub.presentation.databinding.BottomSheetSearchLocationBinding
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet.adapter.KakaoLocationRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BottomSheetDeleteQuestion(
    private val position: Int,
    private val questionCount: Int,
    private val okButtonClickEvent: ((position: Int) -> Unit)? = null
) : BottomSheetDialogFragment() {
    private val binding: BottomSheetDeleteQuestionBinding by lazy {
        BottomSheetDeleteQuestionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.position = position
        binding.questionCount = questionCount

        binding.buttonYes.setOnClickListener {
            okButtonClickEvent?.let { it(position) }
            dismiss()
        }

        binding.buttonNo.setOnClickListener {
            dismiss()
        }
    }
}