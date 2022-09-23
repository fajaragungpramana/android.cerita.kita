package com.github.fajaragungpramana.ceritakita.ui.dialog

import android.Manifest
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.github.fajaragungpramana.ceritakita.databinding.DialogImageBinding
import com.github.fajaragungpramana.ceritakita.widget.app.AppBottomSheet

class AppDialogImage : AppBottomSheet<DialogImageBinding>() {

    override fun onViewBinding(viewGroup: ViewGroup?) =
        DialogImageBinding.inflate(layoutInflater, viewGroup, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        val cameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {

                } else {

                }
            }

        val galleryPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {

                } else {

                }
            }

        viewBinding.mtvCamera.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }
        viewBinding.mtvGallery.setOnClickListener {
            galleryPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

}