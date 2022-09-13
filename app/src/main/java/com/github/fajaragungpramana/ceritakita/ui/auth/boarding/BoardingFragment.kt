package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import android.os.Bundle
import android.view.ViewGroup
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.databinding.FragmentBoardingBinding

class BoardingFragment : AppFragment<FragmentBoardingBinding>() {

    override fun onViewBinding(container: ViewGroup?) =
        FragmentBoardingBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}