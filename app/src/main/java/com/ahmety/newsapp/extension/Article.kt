package com.ahmety.newsapp.extension

import com.ahmety.newsapp.data.ArticleEntity
import com.ahmety.newsapp.model.Article

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}