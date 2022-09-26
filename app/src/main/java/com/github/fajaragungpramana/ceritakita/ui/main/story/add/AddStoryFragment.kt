package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import android.Manifest
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.common.extension.openDeviceSettings
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryAddBinding
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppDialogImage
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStoryFragment : AppFragment<FragmentStoryAddBinding>(), AppObserver {

    private val mViewModel: AddStoryViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryAddBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }

        val cameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {

                } else
                    viewBinding.llcStory.snackBar(
                        message = getString(R.string.need_access_camera),
                        actionMessage = getString(R.string.oke),
                        actionListener = { requireActivity().openDeviceSettings() }
                    )
            }

        val selectedImageGallery =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val uri = it.data?.data
                    viewBinding.aivImage.load(uri)
                }
            }

        val galleryPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    ImagePicker.with(this)
                        .galleryOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent { intent ->
                            selectedImageGallery.launch(intent)
                        }
                } else
                    viewBinding.llcStory.snackBar(
                        message = getString(R.string.need_access_storage),
                        actionMessage = getString(R.string.oke),
                        actionListener = { requireActivity().openDeviceSettings() }
                    )
            }

        viewBinding.aivImage.setOnClickListener {
            AppDialogImage(
                mCameraAccess = {
                    cameraPermission.launch(Manifest.permission.CAMERA)
                },
                mGalleryAccess = {
                    galleryPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            ).show(childFragmentManager, this::class.simpleName)
        }
    }

    override fun onStateObserver() {

    }

}