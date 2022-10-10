package com.github.fajaragungpramana.ceritakita.ui.main.story.detail

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryDetailBinding

class DetailStoryFragment : AppFragment<FragmentStoryDetailBinding>() {

    private val mStory by lazy { arguments?.getParcelable<Story>(Story.INTENT_KEY) }

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryDetailBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.atToolbar.title = mStory?.userName
        viewBinding.atToolbar.onBackPress { findNavController().navigateUp() }

        viewBinding.aivImage.load(mStory?.image)
        viewBinding.mtvDescription.text = mStory?.description
    }

}