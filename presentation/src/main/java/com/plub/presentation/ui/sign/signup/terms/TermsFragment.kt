package com.plub.presentation.ui.sign.signup.terms

import android.graphics.Color
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.SignUpPageType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentTermsBinding
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TermsFragment : BaseFragment<FragmentTermsBinding, TermsPageState, TermsViewModel>(
    FragmentTermsBinding::inflate
) {

    override val viewModel: TermsViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})

    private val normalTerms by lazy {
        listOf(
            binding.includeTermsAgreementPrivacy,
            binding.includeTermsAgreementLocation,
            binding.includeTermsAgreementAge,
            binding.includeTermsAgreementCollect,
            binding.includeTermsAgreementMarketing,
        )
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            initWebViewSetting()
        }

        viewModel.initTerms()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as TermsEvent)
                }
            }
            launch {
                parentViewModel.uiState.collect {
                    viewModel.onInitTermsPageVo(it.termsPageVo)
                }
            }
        }
    }

    private fun initWebViewSetting() {
        normalTerms.forEach {
            it.textViewTerms.apply {
                setBackgroundColor(Color.TRANSPARENT)
                isVerticalScrollBarEnabled = true
            }
        }
    }

    private fun inspectEventFlow(event: TermsEvent) {
        when(event) {
            is TermsEvent.MoveToNext -> {
                parentViewModel.onMoveToNextPage(SignUpPageType.TERMS, event.vo)
            }
        }
    }
}