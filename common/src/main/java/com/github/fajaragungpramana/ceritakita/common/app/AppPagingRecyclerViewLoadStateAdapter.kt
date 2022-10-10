package com.github.fajaragungpramana.ceritakita.common.app

import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.viewbinding.ViewBinding

abstract class AppPagingRecyclerViewLoadStateAdapter<VB : ViewBinding, VH : AppRecyclerViewHolder<LoadState>> :
    LoadStateAdapter<VH>() {

    private lateinit var mViewBinding: VB
    protected val viewBinding: VB
        get() = mViewBinding

    protected abstract fun onViewBinding(parent: ViewGroup): VB

    protected abstract fun onViewHolder(view: View): VH

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VH {
        mViewBinding = onViewBinding(parent)
        return onViewHolder(mViewBinding.root)
    }

    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
        holder.bindItem(loadState, 0)
    }

}