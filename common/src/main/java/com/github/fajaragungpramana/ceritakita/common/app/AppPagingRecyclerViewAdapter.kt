package com.github.fajaragungpramana.ceritakita.common.app

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class AppPagingRecyclerViewAdapter<VB : ViewBinding, M : Any, VH : AppRecyclerViewHolder<M>>(
    diffUtil: DiffUtil.ItemCallback<M>
) : PagingDataAdapter<M, VH>(diffUtil) {

    private lateinit var mViewBinding: VB
    protected val viewBinding: VB
        get() = mViewBinding

    protected abstract fun onViewBinding(viewGroup: ViewGroup): VB

    protected abstract fun onViewHolder(view: View): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        mViewBinding = onViewBinding(parent)
        return onViewHolder(viewBinding.root)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            val item = getItem(position)
            if (item != null) holder.bindItem(item, position)
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    override fun getItemViewType(position: Int) = position

}