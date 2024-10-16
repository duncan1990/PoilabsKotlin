package com.ahmety.kotlinmvvm.data

import com.ahmety.kotlinmvvm.model.Article

data class News(val status: Int?, val msg: String?, val data: List<Article>?) {
    fun isSuccess(): Boolean = (status == 200)
}