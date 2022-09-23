package com.github.fajaragungpramana.ceritakita.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.github.fajaragungpramana.ceritakita.common.app.AppPagingRecyclerViewAdapter
import com.github.fajaragungpramana.ceritakita.common.app.AppRecyclerViewHolder
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.databinding.AdapterStoryBinding

class StoryAdapter :
    AppPagingRecyclerViewAdapter<AdapterStoryBinding, Story, StoryAdapter.ViewHolder>(Story.diffUtil) {

    override fun onViewBinding(viewGroup: ViewGroup) =
        AdapterStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

    override fun onViewHolder(view: View) = ViewHolder(view)

    inner class ViewHolder(view: View) : AppRecyclerViewHolder<Story>(view) {

        override fun bindItem(item: Story, position: Int) {
            viewBinding.aivImage.load(item.image)
            viewBinding.mtvDescription.text = item.description
            viewBinding.mtvName.text = item.userName
        }

    }

}