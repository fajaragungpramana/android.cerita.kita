package com.github.fajaragungpramana.ceritakita.ui.main.story

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryBinding
import com.github.fajaragungpramana.ceritakita.ui.adapter.StoryAdapter
import com.github.fajaragungpramana.ceritakita.ui.state.StoryState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class StoryFragment : AppFragment<FragmentStoryBinding>(), AppObserver {

    private val mViewModel: StoryViewModel by viewModels()

    private lateinit var mStoryAdapter: StoryAdapter

    override fun onViewBinding(container: ViewGroup?) =
        FragmentStoryBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        mViewModel.getStories(StoryRequest())

        mStoryAdapter = StoryAdapter()
        viewBinding.rvStory.layoutManager = LinearLayoutManager(requireActivity())
        viewBinding.rvStory.adapter = mStoryAdapter
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.storyState.collectLatest {
                when (it) {
                    is StoryState.OnStoryLoading -> {}

                    is StoryState.OnStorySuccess -> {
                        mStoryAdapter.submitData(it.data)
                    }

                    is StoryState.OnStoryFailure -> {
                        viewBinding.llStory.snackBar(it.message)
                    }
                }
            }

        }
    }

}