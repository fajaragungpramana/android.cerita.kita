package com.github.fajaragungpramana.ceritakita.ui.main

import android.os.Bundle
import androidx.navigation.Navigation
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppActivity
import com.github.fajaragungpramana.ceritakita.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppActivity<ActivityMainBinding>() {

    val mainController by lazy { Navigation.findNavController(this, R.id.fcv_main) }

    override fun onViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}