package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStoryFragment : AppFragment<FragmentStoryAddBinding>(), AppObserver {

    private val mViewModel: AddStoryViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryAddBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }
    }

    override fun onStateObserver() {

    }

}