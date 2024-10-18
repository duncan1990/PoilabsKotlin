package com.ahmety.kotlinmvvm.extension

import com.ahmety.kotlinmvvm.data.ArticleEntity
import com.ahmety.kotlinmvvm.model.Article

fun ArticleEntity.toArticle(): Article {
    return Article(
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}