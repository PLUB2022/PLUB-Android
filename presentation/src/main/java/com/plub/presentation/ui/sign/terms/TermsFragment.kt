package com.plub.presentation.ui.sign.terms

import android.graphics.Color
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.state.TermsPageState
import com.plub.domain.model.vo.terms.TermsAgreementItemVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentTermsBinding
import com.plub.presentation.databinding.IncludeItemTermsAgreementBinding
import com.plub.presentation.ui.sign.terms.adapter.TermsAgreementAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TermsFragment : BaseFragment<FragmentTermsBinding, TermsPageState, TermsViewModel>(
    FragmentTermsBinding::inflate
) {

    companion object {
        private const val WEB_VIEW_TEXT_ZOOM = 100
        private const val WEB_VIEW_DEFAULT_FONT_SIZE = 12
    }

    override val viewModel: TermsViewModel by viewModels()
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
                viewModel.uiState.collect {
                    it.mapVo
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
                    textZoom = WEB_VIEW_TEXT_ZOOM
                    defaultFontSize = WEB_VIEW_DEFAULT_FONT_SIZE
                    builtInZoomControls = false
                }
            }
        }
    }
}