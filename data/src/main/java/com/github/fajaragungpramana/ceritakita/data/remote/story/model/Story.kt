package com.github.fajaragungpramana.ceritakita.data.remote.story.model

import com.github.fajaragungpramana.ceritakita.data.remote.story.entity.StoryEntity

data class Story(

    val image: String? = null,
    val description: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val date: String? = null

) {
    companion object {
        fun mapList(storyEntity: StoryEntity?): List<Story> {
            val data = arrayListOf<Story>()
            storyEntity?.listStory?.map {
                data.add(
                    Story(
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
    }
}