package com.ahmety.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmety.kotlinmvvm.data.OperationCallback
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.kotlinmvvm.model.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<List<Article>>().apply { postValue(emptyList()) }
    val news: LiveData<List<Article>> = _news

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadNews() {
        _isViewLoading.postValue(true)  // postValue kullanıyoruz
        repository.fetchNews(object : OperationCallback<List<Article>> {
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                error?.let { e -> _onMessageError.postValue(e) }
            }

            override fun onSuccess(data: List<Article>?) {
                _isViewLoading.postValue(false)
                if (data.isNullOrEmpty()) {
                    _isEmptyList.postValue(true)
                } else {
                    _news.postValue(data)
                }
            }
        })
    }
}