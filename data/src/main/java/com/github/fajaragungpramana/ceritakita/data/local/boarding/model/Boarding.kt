package com.github.fajaragungpramana.ceritakita.data.local.boarding.model

import com.github.fajaragungpramana.ceritakita.data.local.boarding.entity.BoardingEntity

data class Boarding(
    val title: String? = null,
    val description: String? = null
) {
    companion object {

        fun mapList(listBoardingEntity: List<BoardingEntity>): List<Boarding> {
            val data = ArrayList<Boarding>()
            listBoardingEntity.map {
                data.add(
                    Boarding(
                        title = it.title,
                        description = it.description
                    )
                )
            }

            return data
        }

    }
}