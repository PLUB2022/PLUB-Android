package com.plub.presentation.ui.sign.terms

import android.graphics.Color
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.TermsPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentTermsBinding
import com.plub.presentation.ui.sign.signup.SignUpFragment
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TermsFragment : BaseFragment<FragmentTermsBinding, TermsPageState, TermsViewModel>(
    FragmentTermsBinding::inflate
) {

    companion object {
        private const val VALUE_WEB_VIEW_TEXT_ZOOM = 100
        private const val VALUE_WEB_VIEW_DEFAULT_FONT_SIZE = 12

        fun newInstance(delegate: SignUpFragment.Delegate) = TermsFragment().apply {
            this.delegate = delegate
        }
    }

    override val viewModel: TermsViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})

    private var delegate:SignUpFragment.Delegate? = null

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

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.moveToNextPage.collect {
                    delegate?.onMoveToNextPage(SignUpPageType.TERMS, it)
                }
            }
            launch {
                parentViewModel.testInitPage.collect {
                    viewModel.onInitPage(it)
                }
            }
        }
    }

    private fun initWebViewSetting() {
        normalTerms.forEach {
            it.webViewTerms.apply {
                setBackgroundColor(Color.TRANSPARENT)
                isVerticalScrollBarEnabled = true
                webViewClient = WebViewClient()
                settings.apply {
                    setSupportZoom(false)
                    textZoom = VALUE_WEB_VIEW_TEXT_ZOOM
                    defaultFontSize = VALUE_WEB_VIEW_DEFAULT_FONT_SIZE
                    builtInZoomControls = false
                }
            }
        }
    }
}