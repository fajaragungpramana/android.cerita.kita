package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.databinding.FragmentBoardingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BoardingFragment : AppFragment<FragmentBoardingBinding>(), AppObserver {

    private val mViewModel: BoardingViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentBoardingBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        mViewModel.getListBoarding()
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.boardingState.collectLatest {
                when (it) {
                    is BoardingState.OnBoardingLoading -> {}
                    is BoardingState.OnBoardingSuccess -> {

                    }
                    is BoardingState.OnBoardingFailure -> {
                    }
                }
            }

        }
    }

}