package com.github.fajaragungpramana.ceritakita.ui.dialog

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.github.fajaragungpramana.ceritakita.databinding.DialogImageBinding
import com.github.fajaragungpramana.ceritakita.widget.app.AppBottomSheet

class AppDialogImage(
    private val mCameraAccess: (View) -> Unit,
    private val mGalleryAccess: (View) -> Unit
) : AppBottomSheet<DialogImageBinding>() {

    override fun onViewBinding(viewGroup: ViewGroup?) =
        DialogImageBinding.inflate(layoutInflater, viewGroup, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.mtvCamera.setOnClickListener {
            mCameraAccess.invoke(it)

            dismiss()
        }
        viewBinding.mtvGallery.setOnClickListener {
            mGalleryAccess.invoke(it)

            dismiss()
        }
    }

}