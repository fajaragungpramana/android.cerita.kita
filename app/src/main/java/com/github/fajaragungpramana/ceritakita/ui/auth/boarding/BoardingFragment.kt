package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.github.fajaragungpramana.ceritakita.R
import com.github.fajaragungpramana.ceritakita.common.app.AppFragment
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver
import com.github.fajaragungpramana.ceritakita.databinding.FragmentBoardingBinding
import com.github.fajaragungpramana.ceritakita.ui.adapter.BoardingAdapter
import com.github.fajaragungpramana.ceritakita.widget.extension.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BoardingFragment : AppFragment<FragmentBoardingBinding>(), AppObserver {

    private val mViewModel: BoardingViewModel by viewModels()

    private val mBoardingAdapter by lazy { BoardingAdapter() }

    private var mBoardingSize = 0

    override fun onViewBinding(container: ViewGroup?) =
        FragmentBoardingBinding.inflate(layoutInflater, container, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        mViewModel.getListBoarding()

        viewBinding.vpBoarding.adapter = mBoardingAdapter
        viewBinding.mbNext.setOnClickListener {
            if (viewBinding.vpBoarding.currentItem == mBoardingSize) {
                val action = BoardingFragmentDirections.actionBoardingFragmentToLoginFragment()
                findNavController().navigate(action)
            } else
                viewBinding.vpBoarding.currentItem += 1
        }
        viewBinding.vpBoarding.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    viewBinding.mbNext.text = if (position == mBoardingSize)
                        getString(R.string.let_start) else getString(R.string.next)
                }
            })
        viewBinding.adiBoarding.viewPager = viewBinding.vpBoarding
    }

    override fun onStateObserver() {
        lifecycleScope.launchWhenStarted {

            mViewModel.boardingState.collectLatest {
                when (it) {
                    is BoardingState.OnBoardingLoading -> {}
                    is BoardingState.OnBoardingSuccess -> {
                        mBoardingSize = (it.listBoarding?.size ?: 0) - 1
                        mBoardingAdapter.submitList(it.listBoarding)
                    }
                    is BoardingState.OnBoardingFailure -> {
                        viewBinding.llcBoarding.snackBar(it.message)
                    }
                }
            }

        }
    }

}