package com.github.fajaragungpramana.ceritakita.ui.main.location

import android.os.Bundle
import android.view.ViewGroup
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.databinding.FragmentLocationBinding

class LocationFragment : AppFragment<FragmentLocationBinding>() {

    override fun onViewBinding(container: ViewGroup?) =
        FragmentLocationBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}