package com.github.fajaragungpramana.ceritakita.ui.auth.register

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentRegisterBinding
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppLoadingDialog
import com.github.fajaragungpramana.ceritakita.ui.state.RegisterState
import com.github.fajaragungpramana.ceritakita.widget.extension.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : AppFragment<FragmentRegisterBinding>(), AppObserver {

    private val mViewModel: RegisterViewModel by viewModels()

    private val mAppLoadingDialog by lazy { AppLoadingDialog(childFragmentManager) }

    override fun onViewBinding(container: ViewGroup?) =
        FragmentRegisterBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }

        viewBinding.aetName.addTextChangedListener {
            viewBinding.mbRegister.isEnabled =
                it.isValidName() && viewBinding.aetEmail.text.isValidEmail() && viewBinding.aetPassword.text.isValidPassword()
        }
        viewBinding.aetEmail.addTextChangedListener {
            viewBinding.mbRegister.isEnabled =
                viewBinding.aetName.text.isValidName() && it.isValidEmail() && viewBinding.aetPassword.text.isValidPassword()
        }
        viewBinding.aetPassword.addTextChangedListener {
            viewBinding.mbRegister.isEnabled =
                viewBinding.aetName.text.isValidName() && viewBinding.aetEmail.text.isValidEmail() && it.isValidPassword()
        }
        viewBinding.mbRegister.setOnClickListener {
            mViewModel.register(
                AuthRequest(
                    name = viewBinding.aetName.text,
                    email = viewBinding.aetEmail.text,
                    password = viewBinding.aetPassword.text
                )
            )
        }

        viewBinding.mtvLogin.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.registerState.collectLatest {
                when (it) {

                    is RegisterState.OnRegisterLoading -> {
                        if (it.isLoading)
                            mAppLoadingDialog.showDialog(this@RegisterFragment::class.java.simpleName)
                        else
                            mAppLoadingDialog.dismiss()
                    }

                    is RegisterState.OnRegisterSuccess -> {
                        toast(it.register?.responseMessage)
                        findNavController().navigateUp()
                    }

                    is RegisterState.OnRegisterFailure -> {
                        viewBinding.llRegister.snackBar(it.message)
                        requireActivity().hideKeyboard()
                    }

                }
            }

        }
    }

}