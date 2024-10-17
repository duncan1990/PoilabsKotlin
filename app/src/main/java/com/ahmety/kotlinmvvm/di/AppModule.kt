package com.ahmety.kotlinmvvm.di

import com.ahmety.kotlinmvvm.data.ApiClient
import com.ahmety.kotlinmvvm.data.NewsRemoteDataSource
import com.ahmety.kotlinmvvm.model.NewsDataSource
import com.ahmety.kotlinmvvm.model.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return ApiClient
    }

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(apiClient: ApiClient): NewsDataSource {
        return NewsRemoteDataSource(apiClient)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(newsDataSource: NewsDataSource): NewsRepository {
        return NewsRepository(newsDataSource)
    }
}