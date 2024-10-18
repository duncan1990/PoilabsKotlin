package com.ahmety.newsapp.model

import com.ahmety.newsapp.data.ArticleDao
import com.ahmety.newsapp.data.OperationCallback
import com.ahmety.newsapp.extension.toArticle
import com.ahmety.newsapp.extension.toEntity
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