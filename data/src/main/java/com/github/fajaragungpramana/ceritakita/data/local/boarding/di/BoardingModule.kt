package com.github.fajaragungpramana.ceritakita.data.local.boarding.di

import com.github.fajaragungpramana.ceritakita.data.domain.boarding.BoardingInteractor
import com.github.fajaragungpramana.ceritakita.data.domain.boarding.BoardingUseCase
import com.github.fajaragungpramana.ceritakita.data.local.boarding.BoardingDataSource
import com.github.fajaragungpramana.ceritakita.data.local.boarding.BoardingRepository
import com.github.fajaragungpramana.ceritakita.data.local.boarding.IBoardingDataSource
import com.github.fajaragungpramana.ceritakita.data.local.boarding.IBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object BoardingModule {

    @Provides
    fun provideDataSource(): IBoardingDataSource = BoardingDataSource()

    @Provides
    fun provideRepository(boardingDataSource: BoardingDataSource): IBoardingRepository =
        BoardingRepository(boardingDataSource)

    @Provides
    fun provideUseCase(boardingRepository: BoardingRepository): BoardingUseCase =
        BoardingInteractor(boardingRepository)

}