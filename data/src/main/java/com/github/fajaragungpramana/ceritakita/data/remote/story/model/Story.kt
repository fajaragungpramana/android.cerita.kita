package com.github.fajaragungpramana.ceritakita.data.remote.story.model

import androidx.recyclerview.widget.DiffUtil
import com.github.fajaragungpramana.ceritakita.data.remote.story.entity.StoryEntity

data class Story(

    val id: String? = null,
    val responseMessage: String? = null,
    val image: String? = null,
    val description: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val date: String? = null

) {
    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Story>() {

            override fun areContentsTheSame(oldItem: Story, newItem: Story) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Story, newItem: Story) =
                oldItem.id == newItem.id

        }

        fun mapList(storyEntity: StoryEntity?): List<Story> {
            val data = arrayListOf<Story>()
            storyEntity?.listStory?.map {
                data.add(
                    Story(
                        id = it.id,
                        image = it.photoUrl,
                        description = it.description,
                        userImage = null,
                        userName = it.name,
                        date = it.createdAt
                    )
                )
            }

            return data
        }

        fun mapObject(storyEntity: StoryEntity?) = Story(
            responseMessage = storyEntity?.message
        )
    }
}