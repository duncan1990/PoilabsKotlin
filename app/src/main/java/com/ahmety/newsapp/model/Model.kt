package com.ahmety.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class NewsResponse (
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>? = null
)

@Parcelize
data class Article (
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
): Parcelable

@Parcelize
data class Source (
    val id: String? = null,
    val name: String? = null
): Parcelable