package com.ahmety.kotlinmvvm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ahmety.kotlinmvvm.data.ArticleDao
import com.ahmety.kotlinmvvm.data.OperationCallback
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.kotlinmvvm.model.NewsDataSource
import com.ahmety.kotlinmvvm.model.NewsRepository
import com.ahmety.kotlinmvvm.model.NewsResponse
import com.ahmety.kotlinmvvm.model.Source
import com.ahmety.kotlinmvvm.viewmodel.NewsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.*
import org.mockito.Mockito.*

class MVVMUnitTest {

    @Mock
    private lateinit var newsDataSource: NewsDataSource

    @Mock
    private lateinit var articleDao: ArticleDao

    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<NewsResponse>>

    private lateinit var viewModel: NewsViewModel
    private lateinit var repository: NewsRepository

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderNewsObserver: Observer<List<Article>>

    private lateinit var newsEmptyList: List<Article>
    private lateinit var newsList: List<Article>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)

        repository = NewsRepository(newsDataSource, articleDao)
        viewModel = NewsViewModel(repository)

        mockData()
        setupObservers()
    }

    @Test
    fun `retrieve news with ViewModel and Repository returns empty data`() {
        with(viewModel) {
            loadNews()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            news.observeForever(onRenderNewsObserver)
        }

        verify(newsDataSource, times(1)).retrieveNews(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(newsEmptyList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.isEmptyList.value == true)
        Assert.assertTrue(viewModel.news.value?.size == 0)
    }

    @Test
    fun `retrieve news with ViewModel and Repository returns full data`() {
        with(viewModel) {
            loadNews()
            isViewLoading.observeForever(isViewLoadingObserver)
            news.observeForever(onRenderNewsObserver)
        }

        verify(newsDataSource, times(1)).retrieveNews(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(newsList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.news.value?.size == 3)
    }

    @Test
    fun `retrieve news with ViewModel and Repository returns an error`() {
        with(viewModel) {
            loadNews()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(newsDataSource, times(1)).retrieveNews(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderNewsObserver = mock(Observer::class.java) as Observer<List<Article>>
    }

    private fun mockData() {
        newsEmptyList = emptyList()
        val mockList: MutableList<Article> = mutableListOf()
        mockList.add(
            Article(
                Source("wired", "Wired"),
                "Joel Khalili",
                "Unmasking Bitcoin Creator Satoshi Nakamoto—Again",
                "A new HBO documentary takes a swing at uncovering the real identity of Satoshi Nakamoto, inventor of Bitcoin. But without incontrovertible proof, the myth lives on.",
                "https://www.wired.com/story/unmasking-bitcoin-creator-satoshi-nakamoto-again/",
                "https://media.wired.com/photos/6703eb3979f13fda7f04485b/191:100/w_1280,c_limit/Satoshi-Nakamoto-biz-1341874258.jpg",
                "2024-10-09T01:00:00Z",
                "Just starting out with stock trading can be an overwhelming experience. Knowing what stocks are good and bad and knowing when to buy or sell doesn’t come naturally."
            )
        )
        mockList.add(Article(
            Source("gizmodo", "Gizmodo.com"),
            "Joe Tilleli",
            "Wall Street in Your Pocket: The Tykr Stock Screener App Will Teach You to Earn Back the Money You Spent on Its Lifetime Plan",
            "Save 83% and an additional $30 when you sign up for the Tykr Stock Screener premium plan lifetime subscription with this code.",
            "https://gizmodo.com/wall-street-in-your-pocket-the-tykr-stock-screener-app-will-teach-you-to-earn-back-the-money-you-spent-on-its-lifetime-plan-2000505735",
            "https://gizmodo.com/app/uploads/2024/10/Tykr.jpg",
            "2024-10-01T18:18:47Z",
            "Just starting out with stock trading can be an overwhelming experience. Knowing what stocks are good and bad and knowing when to buy or sell doesn’t come naturally."
        ))
        mockList.add(Article(
            Source("time", "Time"),
            "Andrew R. Chow",
            "Documentarian Says He’s Solved the Mystery of Bitcoin’s Creator. Insiders Are Extremely Skeptical.",
            "Cullen Hoback's new documentary argues Bitcoin's Satoshi Nakamoto has been hiding in plain sight all along.",
            "https://time.com/7064841/satoshi-nakamoto-hbo-money-electric/",
            "https://api.time.com/wp-content/uploads/2024/10/peter-todd.jpeg?quality=85&w=1200&h=628&crop=1",
            "2024-10-09T01:01:00Z",
            "This article contains spoilers for Money Electric: The Bitcoin Mystery. Who is Bitcoins founder, Satoshi Nakamoto? The question has perplexed and excited cryptocurrency fans ever since Bitcoin"
        ))

        newsList = mockList.toList()
    }
}