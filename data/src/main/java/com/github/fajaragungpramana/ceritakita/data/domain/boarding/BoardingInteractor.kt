package com.github.fajaragungpramana.ceritakita.data.domain.boarding

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.local.boarding.IBoardingRepository
import com.github.fajaragungpramana.ceritakita.data.local.boarding.model.Boarding
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardingInteractor @Inject constructor(private val mBoardingRepository: IBoardingRepository) : BoardingUseCase {

    override suspend fun getListBoarding(): AppResult<Flow<List<Boarding>?>> =
        mBoardingRepository.getListBoarding()

}