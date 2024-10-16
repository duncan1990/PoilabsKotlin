package com.ahmety.kotlinmvvm.model

import com.ahmety.kotlinmvvm.data.OperationCallback

interface NewsDataSource {

    fun retrieveNews(callback: OperationCallback<NewsResponse>)
    fun cancel()
}