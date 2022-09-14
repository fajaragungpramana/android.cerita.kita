package com.github.fajaragungpramana.ceritakita.data.local.boarding

import com.github.fajaragungpramana.ceritakita.data.local.boarding.entity.BoardingEntity

interface IBoardingDataSource {

    suspend fun getListBoarding(): List<BoardingEntity>

}