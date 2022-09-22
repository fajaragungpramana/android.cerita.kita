package com.github.fajaragungpramana.ceritakita.ui.auth.login

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.common.extension.startActivity
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentLoginBinding
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppLoadingDialog
import com.github.fajaragungpramana.ceritakita.ui.main.MainActivity
import com.github.fajaragungpramana.ceritakita.ui.state.LoginState
import com.github.fajaragungpramana.ceritakita.widget.extension.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : AppFragment<FragmentLoginBinding>(), AppObserver {

    private val mViewModel: LoginViewModel by viewModels()

    private val mAppLoadingDialog by lazy { AppLoadingDialog(childFragmentManager) }

    override fun onViewBinding(container: ViewGroup?) =
        FragmentLoginBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }

        viewBinding.aetEmail.addTextChangedListener {
            viewBinding.mbLogin.isEnabled =
                it.isValidEmail() && viewBinding.aetPassword.text.isValidPassword()
        }
        viewBinding.aetPassword.addTextChangedListener {
            viewBinding.mbLogin.isEnabled =
                viewBinding.aetEmail.text.isValidEmail() && it.isValidPassword()
        }
        viewBinding.mbLogin.setOnClickListener {
            mViewModel.login(
                AuthRequest(
                    email = viewBinding.aetEmail.text, password = viewBinding.aetPassword.text
                )
            )
        }
        viewBinding.mtvRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.loginState.collectLatest {
                when (it) {
                    is LoginState.OnLoginLoading -> {
                        if (it.isLoading == true)
                            mAppLoadingDialog.showDialog(this@LoginFragment::class.java.simpleName)
                        else
                            mAppLoadingDialog.dismiss()
                    }
                    is LoginState.OnLoginSuccess -> {
                        toast(it.login?.responseMessage)

                        requireActivity().startActivity<MainActivity>()
                        requireActivity().finish()
                    }
                    is LoginState.OnLoginFailure -> {
                        viewBinding.llLogin.snackBar(it.message)
                        requireActivity().hideKeyboard()
                    }
                }
            }

        }
    }

}