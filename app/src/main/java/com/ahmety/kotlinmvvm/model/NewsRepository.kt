package com.ahmety.kotlinmvvm.model

import com.ahmety.kotlinmvvm.data.OperationCallback

class NewsRepository(private val newsDataSource: NewsDataSource) {

    fun fetchNews(callback: OperationCallback<NewsResponse>) {
        newsDataSource.retrieveNews(callback)
    }

    fun cancel() {
        newsDataSource.cancel()
    }
}