package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import android.Manifest
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.common.extension.openDeviceSettings
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryAddBinding
import com.github.fajaragungpramana.ceritakita.extension.toFile
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppDialogImage
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppLoadingDialog
import com.github.fajaragungpramana.ceritakita.ui.state.AddStoryState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import com.github.fajaragungpramana.ceritakita.widget.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddStoryFragment : AppFragment<FragmentStoryAddBinding>(), AppObserver {

    private val mViewModel: AddStoryViewModel by viewModels()

    private val mStoryRequest by lazy { StoryRequest() }
    private val mAppLoadingDialog by lazy { AppLoadingDialog(childFragmentManager) }

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryAddBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }

        val selectedImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    mStoryRequest.photo = it.data?.data?.toFile(requireActivity())
                    viewBinding.aivImage.load(mStoryRequest.photo)

                    viewBinding.mbShare.isEnabled =
                        mStoryRequest.photo != null && mStoryRequest.description?.isNotEmpty() == true
                }
            }

        val cameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it)
                    ImagePicker.with(this)
                        .cameraOnly()
                        .compress(500)
                        .maxResultSize(500, 500)
                        .createIntent { intent -> selectedImage.launch(intent) }
                else
                    viewBinding.llcStory.snackBar(
                        message = getString(R.string.need_access_camera),
                        actionMessage = getString(R.string.oke),
                        actionListener = { requireActivity().openDeviceSettings() }
                    )
            }

        val galleryPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it)
                    ImagePicker.with(this)
                        .galleryOnly()
                        .compress(500)
                        .maxResultSize(500, 500)
                        .createIntent { intent -> selectedImage.launch(intent) }
                else
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

        viewBinding.aetStory.addTextChangedListener {
            mStoryRequest.description = it

            viewBinding.mbShare.isEnabled =
                mStoryRequest.photo != null && mStoryRequest.description?.isNotEmpty() == true
        }

        viewBinding.mbShare.setOnClickListener { mViewModel.setStories(mStoryRequest) }
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {
            mViewModel.addStoryState.collectLatest {

                when (it) {

                    is AddStoryState.OnAddStoryFailure -> viewBinding.llcStory.snackBar(it.message)

                    is AddStoryState.OnAddStoryLoading -> if (it.isLoading)
                        mAppLoadingDialog.showDialog(this@AddStoryFragment::class.java.simpleName)
                    else
                        mAppLoadingDialog.dismiss()

                    is AddStoryState.OnAddStorySuccess -> {
                        toast(it.story?.responseMessage)
                        findNavController().navigateUp()
                    }
                }

            }
        }
    }

}