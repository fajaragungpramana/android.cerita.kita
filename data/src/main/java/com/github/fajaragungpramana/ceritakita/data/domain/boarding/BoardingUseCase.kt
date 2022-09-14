package com.github.fajaragungpramana.ceritakita.data.domain.boarding

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.local.boarding.model.Boarding
import kotlinx.coroutines.flow.Flow

interface BoardingUseCase {

    suspend fun getListBoarding(): AppResult<Flow<List<Boarding>?>>

}