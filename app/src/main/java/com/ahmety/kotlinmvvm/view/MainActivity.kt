package com.ahmety.kotlinmvvm.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.kotlinmvvm.di.Injection
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.kotlinmvvm.viewmodel.NewsViewModel
import com.ahmety.poilabscase.R

private const val TAG = "CONSOLE"
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<NewsViewModel> {
        Injection.provideViewModelFactory()
    }
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutError: View
    private lateinit var textViewError: TextView
    private lateinit var layoutEmpty: View
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI() {
        recyclerView = findViewById(R.id.recyclerView)
        layoutError = findViewById(R.id.layoutError)
        layoutEmpty = findViewById(R.id.layoutEmpty)
        progressBar = findViewById(R.id.progressBar)
        textViewError = findViewById(R.id.textViewError)

        adapter = NewsAdapter(viewModel.news.value ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    //view model
    private fun setupViewModel() {
        viewModel.news.observe(this, renderNews)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderNews = Observer<List<Article>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        viewModel.loadNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }
}
