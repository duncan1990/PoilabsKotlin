package com.ahmety.kotlinmvvm.di

import com.ahmety.kotlinmvvm.data.ApiClient
import com.ahmety.kotlinmvvm.data.NewsRemoteDataSource
import com.ahmety.kotlinmvvm.model.NewsDataSource
import com.ahmety.kotlinmvvm.model.NewsRepository
import com.ahmety.kotlinmvvm.viewmodel.ViewModelFactory

object Injection {

    private var newsDataSource: NewsDataSource? = null
    private var newsRepository: NewsRepository? = null
    private var newsViewModelFactory: ViewModelFactory? = null

    private fun createNewsDataSource(): NewsDataSource {
        val dataSource = NewsRemoteDataSource(ApiClient)
        newsDataSource = dataSource
        return dataSource
    }

    private fun createNewsRepository(): NewsRepository {
        val repository = NewsRepository(provideDataSource())
        newsRepository = repository
        return repository
    }

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        newsViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = newsDataSource ?: createNewsDataSource()
    private fun providerRepository() = newsRepository ?: createNewsRepository()

    fun provideViewModelFactory() = newsViewModelFactory ?: createFactory()

    fun destroy() {
        newsDataSource = null
        newsRepository = null
        newsViewModelFactory = null
    }
}