package com.ahmety.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmety.kotlinmvvm.data.OperationCallback
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.kotlinmvvm.model.NewsRepository
import com.ahmety.kotlinmvvm.model.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<List<Article>>().apply { value = emptyList() }
    val news: LiveData<List<Article>> = _news

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadNews()" on constructor. Also, if you rotate the screen, the service will not be called.

    init {
        //loadNews()
    }
     */

    fun loadNews() {
        _isViewLoading.value = true
        repository.fetchNews(object : OperationCallback<NewsResponse> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                error?.let { e ->
                    _onMessageError.value = e
                }

            }

            override fun onSuccess(data: List<Article>?) {
                _isViewLoading.value = false
                if (data.isNullOrEmpty()) {
                    _isEmptyList.value = true

                } else {
                    _news.value = data
                }
            }
        })
    }

}