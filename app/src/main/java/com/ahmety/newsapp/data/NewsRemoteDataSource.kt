package com.ahmety.newsapp.data

import com.ahmety.newsapp.model.NewsResponse
import com.ahmety.newsapp.model.NewsDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRemoteDataSource(apiClient: ApiClient) : NewsDataSource {

    private var call: Call<NewsResponse>? = null
    private val service = apiClient.build()

    override fun retrieveNews(callback: OperationCallback<NewsResponse>) {

        call = service.getNews()
        call?.enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.status == "ok")) {
                        callback.onSuccess(it.articles)
                    } else {
                        callback.onError(it.status)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}