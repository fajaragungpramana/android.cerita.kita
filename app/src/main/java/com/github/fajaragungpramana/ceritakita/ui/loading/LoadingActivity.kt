package com.github.fajaragungpramana.ceritakita.ui.loading

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.fajaragungpramana.ceritakita.common.app.AppActivity
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.common.extension.startActivity
import com.github.fajaragungpramana.ceritakita.databinding.ActivityLoadingBinding
import com.github.fajaragungpramana.ceritakita.ui.auth.AuthActivity
import com.github.fajaragungpramana.ceritakita.ui.state.PreferenceState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoadingActivity : AppActivity<ActivityLoadingBinding>(), AppObserver {

    private val mViewModel: LoadingViewModel by viewModels()

    private companion object {
        const val LOADING_TIME = 2000L
    }

    override fun onViewBinding() = ActivityLoadingBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        mViewModel.getPreference()
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.preferenceState.collectLatest {
                when (it) {
                    is PreferenceState.PreferenceLoading -> {}
                    is PreferenceState.PreferenceSuccess -> {
                        Handler(mainLooper).postDelayed({

                            if (it.preference?.isBoarding == false)
                                startActivity<AuthActivity>()
                            else if (it.preference?.isLogin == true && it.preference.token != null)
                                startActivity<AuthActivity>()
                            else
                                startActivity<AuthActivity>()

                            finish()

                        }, LOADING_TIME)
                    }
                    is PreferenceState.PreferenceFailure -> {
                        viewBinding.flLoading.snackBar(it.message)
                    }
                }
            }

        }
    }

}