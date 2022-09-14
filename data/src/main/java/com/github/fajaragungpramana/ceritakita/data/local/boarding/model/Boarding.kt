package com.github.fajaragungpramana.ceritakita.data.local.boarding.model

import androidx.recyclerview.widget.DiffUtil
import com.github.fajaragungpramana.ceritakita.data.local.boarding.entity.BoardingEntity

data class Boarding(
    val image: Int? = null,
    val title: String? = null,
    val description: String? = null
) {
    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Boarding>() {

            override fun areContentsTheSame(oldItem: Boarding, newItem: Boarding) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Boarding, newItem: Boarding) =
                oldItem.title == newItem.title

        }

        fun mapList(listBoardingEntity: List<BoardingEntity>): List<Boarding> {
            val data = ArrayList<Boarding>()
            listBoardingEntity.map {
                data.add(
                    Boarding(
                        image = it.image,
                        title = it.title,
                        description = it.description
                    )
                )
            }

            return data
        }

    }
}