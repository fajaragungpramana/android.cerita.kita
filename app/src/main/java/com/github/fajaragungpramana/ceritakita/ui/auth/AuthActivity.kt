package com.github.fajaragungpramana.ceritakita.ui.auth

import android.os.Bundle
import com.github.fajaragungpramana.ceritakita.common.app.AppActivity
import com.github.fajaragungpramana.ceritakita.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppActivity<ActivityAuthBinding>() {

    override fun onViewBinding() = ActivityAuthBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}