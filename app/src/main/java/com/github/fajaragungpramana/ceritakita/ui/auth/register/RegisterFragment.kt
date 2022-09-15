package com.github.fajaragungpramana.ceritakita.ui.auth.register

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : AppFragment<FragmentRegisterBinding>(), AppObserver {

    private val mViewModel: RegisterViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentRegisterBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }
        viewBinding.mtvLogin.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onStateObserver() {

    }

}