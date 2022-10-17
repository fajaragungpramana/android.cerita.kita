package com.github.fajaragungpramana.ceritakita.ui.main.location

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.databinding.FragmentLocationBinding
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppLoadingDialog
import com.github.fajaragungpramana.ceritakita.ui.state.LocationState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okio.ArrayIndexOutOfBoundsException

@AndroidEntryPoint
class LocationFragment : AppFragment<FragmentLocationBinding>(), AppObserver, OnMapReadyCallback {

    private val mViewModel: LocationViewModel by viewModels()

    private val mAppLoadingDialog by lazy { AppLoadingDialog(childFragmentManager) }

    private lateinit var mGoogleMap: GoogleMap

    override fun onViewBinding(container: ViewGroup?) =
        FragmentLocationBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mViewModel.getStoryLocations()
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenCreated {
            mViewModel.locationState.collectLatest {

                when (it) {
                    is LocationState.OnLocationStoryLoading -> if (it.isLoading)
                        mAppLoadingDialog.showDialog(this@LocationFragment::class.java.simpleName)
                    else
                        mAppLoadingDialog.dismiss()

                    is LocationState.OnLocationStoryFailure -> viewBinding.fcvMap.snackBar(it.message)

                    is LocationState.OnLocationStorySuccess -> {
                        if (::mGoogleMap.isInitialized) {
                            it.listLocation?.forEach { story ->
                                mGoogleMap.addMarker(
                                    MarkerOptions()
                                        .position(
                                            LatLng(
                                                story.latitude ?: 0.0,
                                                story.longitude ?: 0.0
                                            )
                                        )
                                        .title(story.userName)
                                )
                            }

                            try {
                                val firstStory = it.listLocation?.get(0)
                                val firstLatLng =
                                    LatLng(
                                        firstStory?.latitude ?: 0.0,
                                        firstStory?.longitude ?: 0.0
                                    )

                                val lastStory = it.listLocation?.get(it.listLocation.size - 1)
                                val lastLatLng =
                                    LatLng(lastStory?.latitude ?: 0.0, lastStory?.longitude ?: 0.0)

                                mGoogleMap.moveCamera(
                                    CameraUpdateFactory.newLatLngBounds(
                                        LatLngBounds(firstLatLng, lastLatLng), 14
                                    )
                                )
                            } catch (e: ArrayIndexOutOfBoundsException) {
                                e.printStackTrace()
                                viewBinding.fcvMap.snackBar(getString(R.string.google_map_error))
                            }
                        } else
                            viewBinding.fcvMap.snackBar(getString(R.string.google_map_error))
                    }
                }

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (!::mGoogleMap.isInitialized) mGoogleMap = googleMap
    }

}