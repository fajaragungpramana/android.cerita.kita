package com.github.fajaragungpramana.ceritakita.data.remote.story.di

import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryInteractor
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryUseCase
import com.github.fajaragungpramana.ceritakita.data.remote.story.IStoryDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.story.IStoryRepository
import com.github.fajaragungpramana.ceritakita.data.remote.story.StoryDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.story.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object StoryModule {

    @Provides
    fun provideDataSource(retrofit: Retrofit): IStoryDataSource =
        retrofit.create(IStoryDataSource::class.java)

    @Provides
    fun provideStoryDataSource(storyDataSource: IStoryDataSource): StoryDataSource =
        StoryDataSource(storyDataSource)

    @Provides
    fun provideStoryRepository(storyDataSource: IStoryDataSource): IStoryRepository =
        StoryRepository(storyDataSource)

    @Provides
    fun provideUseCase(storyRepository: StoryRepository): StoryUseCase =
        StoryInteractor(storyRepository)

}