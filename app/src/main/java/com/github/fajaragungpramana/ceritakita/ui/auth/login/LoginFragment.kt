package com.github.fajaragungpramana.ceritakita.ui.auth.login

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentLoginBinding
import com.github.fajaragungpramana.ceritakita.ui.state.LoginState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : AppFragment<FragmentLoginBinding>(), AppObserver {

    private val mViewModel: LoginViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentLoginBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }
        viewBinding.mbLogin.setOnClickListener { mViewModel.login(AuthRequest(email = "ff", password = "123456")) }
        viewBinding.mtvRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.loginState.collectLatest {
                when (it) {
                    is LoginState.OnLoginLoading -> {}
                    is LoginState.OnLoginSuccess -> {}
                    is LoginState.OnLoginFailure -> {
                        viewBinding.llLogin.snackBar(it.message)
                    }
                }
            }

        }
    }

}