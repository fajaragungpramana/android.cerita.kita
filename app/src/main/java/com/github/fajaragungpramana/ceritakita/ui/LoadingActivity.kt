package com.github.fajaragungpramana.ceritakita.ui

import android.os.Bundle
import com.github.fajaragungpramana.ceritakita.common.app.AppActivity
import com.github.fajaragungpramana.ceritakita.databinding.ActivityLoadingBinding

class LoadingActivity : AppActivity<ActivityLoadingBinding>() {

    override fun onViewBinding() = ActivityLoadingBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}