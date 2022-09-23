package com.github.fajaragungpramana.ceritakita.common.app

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class AppRecyclerViewAdapter<VB : ViewBinding, M : Any, VH : AppRecyclerViewHolder<M>>(
    diffUtil: DiffUtil.ItemCallback<M>
) : ListAdapter<M, VH>(diffUtil) {

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
            holder.bindItem(getItem(position), position)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

    override fun getItemViewType(position: Int) = position

}