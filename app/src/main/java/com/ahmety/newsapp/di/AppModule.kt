package com.ahmety.newsapp.di

import android.content.Context
import androidx.room.Room
import com.ahmety.newsapp.data.ApiClient
import com.ahmety.newsapp.data.ArticleDao
import com.ahmety.newsapp.data.NewsDatabase
import com.ahmety.newsapp.data.NewsRemoteDataSource
import com.ahmety.newsapp.model.NewsDataSource
import com.ahmety.newsapp.model.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideNewsRepository(newsDataSource: NewsDataSource, articleDao: ArticleDao): NewsRepository {
        return NewsRepository(newsDataSource, articleDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NewsDatabase {
        return Room.databaseBuilder(
            appContext,
            NewsDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: NewsDatabase): ArticleDao {
        return database.articleDao()
    }
}