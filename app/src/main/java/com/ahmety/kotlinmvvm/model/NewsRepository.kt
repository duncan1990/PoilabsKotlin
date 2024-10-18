package com.ahmety.kotlinmvvm.model

import com.ahmety.kotlinmvvm.data.ArticleDao
import com.ahmety.kotlinmvvm.data.ArticleEntity
import com.ahmety.kotlinmvvm.data.OperationCallback
import com.ahmety.kotlinmvvm.extension.toArticle
import com.ahmety.kotlinmvvm.extension.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val articleDao: ArticleDao
) {

    fun fetchNews(callback: OperationCallback<List<Article>>) {
        newsDataSource.retrieveNews(object : OperationCallback<NewsResponse> {
            override fun onError(error: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    val savedArticles = articleDao.getAllArticles()
                    if (savedArticles.isNotEmpty()) {
                        callback.onSuccess(savedArticles.map { it.toArticle() })
                    } else {
                        callback.onError("No data available from API or local database")
                    }
                }
            }

            override fun onSuccess(data: List<Article>?) {
                data?.let { articles ->
                    val articleEntities = articles.map { it.toEntity() }
                    CoroutineScope(Dispatchers.IO).launch {
                        articleDao.insertArticles(articleEntities)
                    }
                    callback.onSuccess(articles)
                } ?: run {
                    callback.onError("No data received from API")
                }
            }
        })
    }
}