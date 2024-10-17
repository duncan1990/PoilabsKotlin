package com.ahmety.kotlinmvvm.view.news

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmety.kotlinmvvm.di.Injection
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.kotlinmvvm.view.news.adapter.NewsAdapter
import com.ahmety.kotlinmvvm.viewmodel.NewsViewModel
import com.ahmety.poilabscase.R
import com.ahmety.poilabscase.databinding.FragmentNewsListBinding

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NewsViewModel> {
        Injection.provideViewModelFactory()
    }
    private var adapter: NewsAdapter? = null

    override fun onResume() {
        super.onResume()
        viewModel.loadNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        adapter = NewsAdapter { article ->
            findNavController().navigate(
                NewsListFragmentDirections.actionNewsListFragmentToArticleDetailFragment(
                    article
                )
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.news.observe(viewLifecycleOwner) { articles ->
            adapter?.submitList(articles)
        }
        setupSearch()

    }

    private fun setupViewModel() {
        viewModel.news.observe(viewLifecycleOwner, renderNews)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val renderNews = Observer<List<Article>> {
        binding.layoutError.root.visibility = View.GONE
        binding.layoutEmpty.root.visibility = View.GONE
        adapter?.submitList(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        binding.layoutError.root.visibility = View.VISIBLE
        binding.layoutEmpty.root.visibility = View.GONE
        binding.layoutError.textViewError.text = getString(R.string.error_text, it)
    }

    private val emptyListObserver = Observer<Boolean> {
        binding.layoutEmpty.root.visibility = View.VISIBLE
        binding.layoutError.root.visibility = View.GONE
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchQuery = s.toString().lowercase()
                filterList(searchQuery)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterList(query: String) {
        val filteredList = viewModel.news.value?.filter { article ->
            article.title?.lowercase()?.contains(query) == true
        } ?: emptyList()

        adapter?.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Injection.destroy()
        adapter = null
    }

}