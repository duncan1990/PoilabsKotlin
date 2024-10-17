package com.ahmety.kotlinmvvm.view.articledetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ahmety.kotlinmvvm.extension.loadImages
import com.ahmety.poilabscase.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.article.urlToImage?.let { img ->
            binding.ivArticle.loadImages(img)
        }
    }

}