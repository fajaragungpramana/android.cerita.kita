package com.github.fajaragungpramana.ceritakita.ui.loading

import android.os.Bundle
import android.os.Handler
import com.github.fajaragungpramana.ceritakita.common.app.AppActivity
import com.github.fajaragungpramana.ceritakita.common.extension.startActivity
import com.github.fajaragungpramana.ceritakita.databinding.ActivityLoadingBinding
import com.github.fajaragungpramana.ceritakita.ui.auth.AuthActivity

class LoadingActivity : AppActivity<ActivityLoadingBinding>() {

    private companion object {
        const val LOADING_TIME = 2000L
    }

    override fun onViewBinding() = ActivityLoadingBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        Handler(mainLooper).postDelayed({
            startActivity<AuthActivity>()
            finish()
        }, LOADING_TIME)
    }

}