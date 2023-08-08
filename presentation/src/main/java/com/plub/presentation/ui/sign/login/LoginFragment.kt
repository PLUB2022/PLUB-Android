package com.plub.presentation.ui.sign.login

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.enums.ToastType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentLoginBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.ui.main.MainActivity
import com.plub.presentation.util.PlubToast
import com.plub.presentation.util.onThrottleClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginPageState, LoginViewModel>(
    FragmentLoginBinding::inflate
) {

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInResultLauncher:ActivityResultLauncher<Intent>

    @Inject
    lateinit var commonDialog: CommonDialog

    override fun initView() {
        setGoogleSignInResultLauncher()
        initGoogleLogin()

        binding.apply {
            vm = viewModel

            testSignup.onThrottleClick {
                val action = LoginFragmentDirections.actionLoginToSignUp()
                findNavController().navigate(action)
            }

            textViewTerms.apply {
                movementMethod = LinkMovementMethod.getInstance()
                highlightColor = Color.TRANSPARENT
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as LoginEvent)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkAlreadyGoogleSignIn()
    }

    private fun checkAlreadyGoogleSignIn() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        account?.let {
            viewModel.alreadyGoogleSignIn(it)
        }
    }

    private fun initGoogleLogin() {
        val googleClientId = getString(R.string.web_google_client_id)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(googleClientId)
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun signInGoogle() {
        val intent = googleSignInClient.signInIntent
        googleSignInResultLauncher.launch(intent)
    }

    private fun setGoogleSignInResultLauncher() {
        googleSignInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.handleGoogleSignInResult(task)
        }
    }

    private fun signInKakao() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            signInKakaoApp()
        } else {
            signInKakaoEmail()
        }
    }

    private fun signInKakaoApp() {
        UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
            viewModel.handleKakaoSignInAppResult(token,error)
        }
    }

    private fun signInKakaoEmail() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
            viewModel.handleKakaoSignInEmailResult(token,error)
        }
    }

    private fun inspectEventFlow(event: LoginEvent) {
        when(event) {
            is LoginEvent.GoToMain -> {
                val intent = IntentUtil.getMainActivityIntent(requireContext())
                startActivity(intent)
                requireActivity().finish()
            }
            is LoginEvent.GoToSignUp -> {
                val action = LoginFragmentDirections.actionLoginToSignUp()
                findNavController().navigate(action)
            }
            is LoginEvent.GoToTerms -> {goToTerm(event.type)}
            is LoginEvent.SignInGoogle -> signInGoogle()
            is LoginEvent.SignInKakao -> signInKakao()
            is LoginEvent.SignInKakaoEmail -> signInKakaoEmail()
            is LoginEvent.FailSocialLogin -> failToast()
            is LoginEvent.StoppedAccountDialog -> {
                showStoppedDialog()
            }
        }
    }

    private fun goToTerm(type : TermsType){
        when(type){
            TermsType.PRIVACY -> goToPersonalPolices()
            TermsType.SERVICE -> goToServicePolices()
            else -> {}
        }
    }

    private fun goToServicePolices(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.service_policies)))
        )
    }

    private fun goToPersonalPolices(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.personal_policies)))
        )
    }

    private fun showStoppedDialog(){
        commonDialog
            .setTitle(R.string.error_stopped_account_title)
            .setDescription(R.string.error_stopped_account_description)
            .setPositiveButton(R.string.word_ok){
                commonDialog.dismiss()
            }
            .show()
    }

    private fun failToast(){
        context?.let {
            PlubToast.createToast(ToastType.ERROR,
                it, R.string.error_fail_social_login,Toast.LENGTH_SHORT)
        }
    }
}