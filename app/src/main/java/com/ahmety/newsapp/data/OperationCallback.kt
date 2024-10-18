package com.ahmety.newsapp.data

import com.ahmety.newsapp.model.Article

interface OperationCallback<T> {
    fun onSuccess(data: List<Article>?)
    fun onError(error: String?)
}