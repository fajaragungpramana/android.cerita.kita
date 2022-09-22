package com.github.fajaragungpramana.ceritakita.ui.main.story

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryBinding
import com.github.fajaragungpramana.ceritakita.ui.state.StoryState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class StoryFragment : AppFragment<FragmentStoryBinding>(), AppObserver {

    private val mViewModel: StoryViewModel by viewModels()

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        mViewModel.getStories(StoryRequest())
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.storyState.collectLatest {
                when (it) {
                    is StoryState.OnStoryLoading -> {}

                    is StoryState.OnStorySuccess -> {

                    }

                    is StoryState.OnStoryFailure -> {
                        viewBinding.llStory.snackBar(it.message)
                    }
                }
            }

        }
    }

}