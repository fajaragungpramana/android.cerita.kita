package com.github.fajaragungpramana.ceritakita.ui.main.location

import android.os.Bundle
import android.view.ViewGroup
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.databinding.FragmentLocationBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class LocationFragment : AppFragment<FragmentLocationBinding>(), OnMapReadyCallback {

    override fun onViewBinding(container: ViewGroup?) =
        FragmentLocationBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

    }

}