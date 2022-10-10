package com.github.fajaragungpramana.ceritakita.ui.main.story

import android.os.Bundle
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.common.extension.startActivity
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.databinding.FragmentStoryBinding
import com.github.fajaragungpramana.ceritakita.ui.adapter.LoadStateAdapter
import com.github.fajaragungpramana.ceritakita.ui.adapter.StoryAdapter
import com.github.fajaragungpramana.ceritakita.ui.loading.LoadingActivity
import com.github.fajaragungpramana.ceritakita.ui.state.StoryState
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import com.github.fajaragungpramana.ceritakita.widget.extension.toast
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

        viewBinding.atToolbar.onBackPress {
            mViewModel.logout()
            toast(getString(R.string.logout))

            requireActivity().startActivity<LoadingActivity>()
            requireActivity().finishAffinity()
        }

        mStoryAdapter = StoryAdapter {
            val action = StoryFragmentDirections.actionStoryFragmentToDetailStoryFragment(it)
            findNavController().navigate(action)
        }
        viewBinding.rvStory.layoutManager = LinearLayoutManager(requireActivity())
        viewBinding.rvStory.adapter = mStoryAdapter.withLoadStateFooter(LoadStateAdapter())

        viewBinding.alrStory.setOnRefreshListener { mStoryAdapter.refresh() }

        viewBinding.rvStory.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                    viewBinding.fabAddStory.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setStartDelay(300)
                        .start()
                else
                    viewBinding.fabAddStory.animate()
                        .alpha(0f)
                        .translationY(300f)
                        .setStartDelay(300)
                        .start()
            }

        })

        viewBinding.fabAddStory.setOnClickListener {
            val action = StoryFragmentDirections.actionStoryFragmentToAddStoryFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.storyState.collectLatest {
                when (it) {
                    is StoryState.OnStoryLoading -> {}

                    is StoryState.OnStorySuccess -> {
                        viewBinding.alrStory.isRefreshing = false
                        mStoryAdapter.submitData(it.data)
                    }

                    is StoryState.OnStoryFailure -> {
                        viewBinding.alrStory.isRefreshing = false
                        viewBinding.llStory.snackBar(it.message)
                    }
                }
            }

        }
    }

}