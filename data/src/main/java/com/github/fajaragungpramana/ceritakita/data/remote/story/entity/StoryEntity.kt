package com.github.fajaragungpramana.ceritakita.data.remote.story.entity

import com.google.gson.annotations.SerializedName

data class StoryEntity(

    @SerializedName("error")
    val error: Boolean? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("listStory")
    val listStory: List<Result>? = null

) {

    data class Result(

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("description")
        val description: String? = null,

        @SerializedName("photoUrl")
        val photoUrl: String? = null,

        @SerializedName("createdAt")
        val createdAt: String? = null,

        @SerializedName("lat")
        val lat: Double? = null,

        @SerializedName("lon")
        val lon: Double? = null

    )

}