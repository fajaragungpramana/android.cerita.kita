package com.github.fajaragungpramana.ceritakita.ui.main.story

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryFragment : AppFragment<FragmentStoryBinding>() {

    private val mViewModel: StoryViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}