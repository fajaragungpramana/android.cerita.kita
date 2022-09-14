package com.github.fajaragungpramana.ceritakita.data.local.boarding

import com.github.fajaragungpramana.ceritakita.data.R
import com.github.fajaragungpramana.ceritakita.data.di.Data
import com.github.fajaragungpramana.ceritakita.data.local.boarding.entity.BoardingEntity
import javax.inject.Inject

class BoardingDataSource @Inject constructor() : IBoardingDataSource {

    override suspend fun getListBoarding(): List<BoardingEntity> {
        val context = Data.context
        return if (context != null) {
            val data = ArrayList<BoardingEntity>()
            val resources = context.resources

            val listBoardingTitle = resources.getStringArray(R.array.boarding_title)
            val listBoardingDescription = resources.getStringArray(R.array.boarding_description)

            for (i in listBoardingTitle.indices) {
                data.add(
                    BoardingEntity(
                        title = listBoardingTitle[i],
                        description = listBoardingDescription[i]
                    )
                )
            }

            data
        } else {
            throw NullPointerException("Data.context is null.")
        }
    }

}