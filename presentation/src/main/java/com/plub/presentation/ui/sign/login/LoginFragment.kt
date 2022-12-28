package com.plub.presentation.ui.sign.login

import android.content.Intent
import android.graphics.Color
import android.text.method.LinkMovementMethod
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.plub.domain.model.state.LoginPageState
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginPageState, LoginViewModel>(
    FragmentLoginBinding::inflate
) {

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInResultLauncher:ActivityResultLauncher<Intent>

    override fun initView() {
        setGoogleSignInResultLauncher()
        initGoogleLogin()

        binding.apply {
            vm = viewModel

            textViewTerms.apply {
                movementMethod = LinkMovementMethod.getInstance()
                highlightColor = Color.TRANSPARENT
            }

            textViewSignUp.setOnClickListener {
                //TODO 테스트 이후 뷰 xml과 같이 지워야함
                goToSignUp()
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.signInGoogle.collect {
                    signInGoogle()
                }
            }

            launch {
                viewModel.signInKakao.collect {
                    signInKakao()
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

    }

    private fun goToSignUp() {
        val action = LoginFragmentDirections.actionLoginToSignUp()
        findNavController().navigate(action)
    }
}