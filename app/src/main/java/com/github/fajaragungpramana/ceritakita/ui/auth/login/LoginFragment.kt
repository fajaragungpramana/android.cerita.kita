package com.github.fajaragungpramana.ceritakita.ui.auth.login

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : AppFragment<FragmentLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentLoginBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }
    }

}