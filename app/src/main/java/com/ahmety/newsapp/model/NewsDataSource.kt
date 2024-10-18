package com.ahmety.newsapp.model

import com.ahmety.newsapp.data.OperationCallback

interface NewsDataSource {

    fun retrieveNews(callback: OperationCallback<NewsResponse>)
    fun cancel()
}