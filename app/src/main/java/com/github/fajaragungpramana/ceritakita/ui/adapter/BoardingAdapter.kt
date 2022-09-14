package com.github.fajaragungpramana.ceritakita.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.github.fajaragungpramana.ceritakita.common.app.AppRecyclerViewAdapter
import com.github.fajaragungpramana.ceritakita.common.app.AppRecyclerViewHolder
import com.github.fajaragungpramana.ceritakita.data.local.boarding.model.Boarding
import com.github.fajaragungpramana.ceritakita.databinding.AdapterBoardingBinding

class BoardingAdapter :
    AppRecyclerViewAdapter<AdapterBoardingBinding, Boarding, BoardingAdapter.ViewHolder>(Boarding.diffUtil) {

    override fun onViewBinding(viewGroup: ViewGroup) =
        AdapterBoardingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

    override fun onViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(view: View) : AppRecyclerViewHolder<Boarding>(view) {

        override fun bindItem(item: Boarding, position: Int) {
            viewBinding.ivImage.load(item.image)
            viewBinding.mtvTitle.text = item.title
            viewBinding.mtvDescription.text = item.description
        }

    }

}