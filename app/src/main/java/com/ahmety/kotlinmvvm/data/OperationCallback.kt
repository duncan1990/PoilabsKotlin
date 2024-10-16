package com.ahmety.kotlinmvvm.data

import com.ahmety.kotlinmvvm.model.Article

interface OperationCallback<T> {
    fun onSuccess(data: List<Article>?)
    fun onError(error: String?)
}