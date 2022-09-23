package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryAddBinding
import com.github.fajaragungpramana.ceritakita.ui.dialog.AppDialogImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStoryFragment : AppFragment<FragmentStoryAddBinding>(), AppObserver {

    private val mViewModel: AddStoryViewModel by viewModels()

    private val mAppDialogImage by lazy { AppDialogImage() }

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryAddBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }
        viewBinding.aivImage.setOnClickListener {
            mAppDialogImage.show(childFragmentManager, this::class.simpleName)
        }
    }

    override fun onStateObserver() {

    }

}