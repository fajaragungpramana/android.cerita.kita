package com.github.fajaragungpramana.ceritakita.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.github.fajaragungpramana.ceritakita.common.app.AppPagingRecyclerViewLoadStateAdapter
import com.github.fajaragungpramana.ceritakita.common.app.AppRecyclerViewHolder
import com.github.fajaragungpramana.ceritakita.databinding.AdapterLoadStateBinding

class LoadStateAdapter :
    AppPagingRecyclerViewLoadStateAdapter<AdapterLoadStateBinding, LoadStateAdapter.ViewHolder>() {

    override fun onViewBinding(parent: ViewGroup) =
        AdapterLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(view: View) : AppRecyclerViewHolder<LoadState>(view) {

        override fun bindItem(item: LoadState, position: Int) {
            viewBinding.lavProgress.isVisible = item is LoadState.Loading
            viewBinding.mtvError.isVisible = item is LoadState.Error
        }

    }

}