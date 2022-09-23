package com.github.fajaragungpramana.ceritakita.data.local.boarding

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.extension.connection
import com.github.fajaragungpramana.ceritakita.data.local.boarding.model.Boarding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BoardingRepository @Inject constructor(private val mBoardingDataSource: IBoardingDataSource) :
    IBoardingRepository {

    override suspend fun getListBoarding(): AppResult<Flow<List<Boarding>?>> =
        connection {
            AppResult.OnSuccess(
                flow { emit(Boarding.mapList(mBoardingDataSource.getListBoarding())) }
                    .flowOn(Dispatchers.IO)
            )
        }

}